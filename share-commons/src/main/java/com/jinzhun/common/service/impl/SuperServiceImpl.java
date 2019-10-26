package com.jinzhun.common.service.impl;

import java.io.Serializable;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinzhun.common.exception.IdempotencyException;
import com.jinzhun.common.exception.LockException;
import com.jinzhun.common.lock.DistributedLock;
import com.jinzhun.common.service.IdempotencyService;

import cn.hutool.core.util.StrUtil;

public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IdempotencyService<T> {

	@Override
	public boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
		return saveIdempotency(entity, lock, lockKey, countWrapper, null);
	}

	@Override
	public boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
        if (lock == null) {
            throw new LockException("DistributedLock is null");
        }
        if (StrUtil.isEmpty(lockKey)) {
            throw new LockException("lockKey is null");
        }
        try {
            //加锁
            boolean isLock = lock.lock(lockKey);
            if (isLock) {
                //判断记录是否已存在
                int count = super.count(countWrapper);
                if (count == 0) {
                    return super.save(entity);
                } else {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    throw new IdempotencyException(msg);
                }
            } else {
                throw new LockException("锁等待超时");
            }
        } finally {
            lock.releaseLock(lockKey);
        }
	}

	@Override
	public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
		return this.saveOrUpdateIdempotency(entity, lock, lockKey, countWrapper, null);
	}

	@Override
	public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper,String msg) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    return this.saveIdempotency(entity, lock, lockKey, countWrapper, msg);
                } else {
                    return updateById(entity);
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
	}

}
