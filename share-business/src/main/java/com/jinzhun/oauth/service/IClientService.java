package com.jinzhun.oauth.service;

import java.util.Map;

import com.jinzhun.common.model.PageResult;
import com.jinzhun.common.model.Result;
import com.jinzhun.common.service.IdempotencyService;
import com.jinzhun.oauth.model.Client;

public interface IClientService extends IdempotencyService<Client> {

	/**
	 * 保存记录
	 */
	Result<Client> saveClient(Client clientDto);
	
	/**
	 * 删除记录
	 */
	void delClient(long id);
	
	/**
	 * 查询应用列表
	 * 
	 * @param params
	 * @param isPage 是否分页
	 */
	PageResult<Client> listClent(Map<String, Object> params, boolean isPage);
}
