package com.jinzhun.oauth.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinzhun.common.constant.CommonConstant;
import com.jinzhun.common.constant.SecurityConstants;
import com.jinzhun.common.lock.DistributedLock;
import com.jinzhun.common.model.PageResult;
import com.jinzhun.common.model.Result;
import com.jinzhun.common.service.impl.SuperServiceImpl;
import com.jinzhun.oauth.mapper.ClientMapper;
import com.jinzhun.oauth.model.Client;
import com.jinzhun.oauth.service.IClientService;
import com.jinzhun.redis.repository.RedisRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientServiceImpl extends SuperServiceImpl<ClientMapper, Client> implements IClientService {
	
	private final static String LOCK_KEY_CLIENTID = CommonConstant.LOCK_KEY_PREFIX + "clientId:";
    
	@Autowired private RedisRepository redisRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private DistributedLock lock;
	
	@Override
	public Result<Client> saveClient(Client client) {
		client.setClientSecret(passwordEncoder.encode(client.getClientSecretStr()));
		String clientId = client.getClientId();
		super.saveOrUpdateIdempotency(client, lock, LOCK_KEY_CLIENTID + clientId,new QueryWrapper<Client>().eq("client_id", clientId), clientId + "已存在");
		// 写入redis缓存
		redisRepository.set(clientRedisKey(client.getClientId()), client);
		
		log.info("saveClient:{}",client.toString());
		
		return Result.succeed("操作成功");
	}
	
	@Override
	public void delClient(long id) {
        String clientId = baseMapper.selectById(id).getClientId();
        baseMapper.deleteById(id);
        redisRepository.del(clientRedisKey(clientId));
        
        log.info("delClient:{}",clientRedisKey(clientId));
	}
	
	@Override
	public PageResult<Client> listClent(Map<String, Object> params, boolean isPage) {
		Page<Client> page;
		if (isPage) {
			page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
		} else {
			page = new Page<>(1, -1);
		}
		List<Client> list = baseMapper.findList(page, params);
		page.setRecords(list);
		return PageResult.<Client>builder().data(list).code(0).count(page.getTotal()).build();
	}

    private String clientRedisKey(String clientId) {
        return SecurityConstants.CACHE_CLIENT_KEY + ":" + clientId;
    }
}
