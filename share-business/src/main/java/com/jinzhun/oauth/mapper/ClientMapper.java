package com.jinzhun.oauth.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinzhun.db.mapper.SuperMapper;
import com.jinzhun.oauth.model.Client;

/**
 * Client-Mapper Interface
 */
public interface ClientMapper extends SuperMapper<Client> {

	List<Client> findList(Page<Client> page, @Param("params") Map<String, Object> params );
}
