package com.jinzhun.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinzhun.common.lock.DistributedLock;

/**
 * 幂等性操作接口
 *
 * @param <T>
 */
public interface IdempotencyService<T> extends IService<T> {

	boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);
	
	boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);
	
	boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);
	
	boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);
}
