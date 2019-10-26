package com.jinzhun.oauth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinzhun.common.model.SuperEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端应用实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
public class Client extends SuperEntity<Client> {
	
	private static final long serialVersionUID = -8185413579135897885L;
	
	/**
	 * 客户端ID/应用ID
	 */
	private String clientId;
	
	/**
	 * 资源ID列表
	 */
	private String resourceIds = "";
	
	/**
	 * 应用密钥
	 */
	private String clientSecret;
	
	/**
	 * 客户端密钥字符串
	 */
	private String clientSecretStr;
	
	/**
	 * 授权范围
	 */
	private String scope = "all";
	
	/**
	 * 授权模式：授权码、用户名密码、刷新令牌、客户端凭证
	 */
	private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
	
	/**
	 * 重定向URI
	 */
	private String webServerRedirectUri;
	
	/**
	 * 授权信息
	 */
	private String authorities = "";
	
	/**
	 * 访问令牌有效时间，默认为18000秒
	 */
	@TableField(value = "access_token_validity")
	private Integer accessTokenValiditySeconds = 18000;
	
	/**
	 * 刷新令牌有效时间，默认为28800秒
	 */
	@TableField(value = "refresh_token_validity")
	private Integer refreshTokenValiditySeconds = 28800;
	
	/**
	 * 附加信息
	 */
	private String additionalInformation = "{}";
	
	/**
	 * 默认接受自动授权
	 */
	private String autoapprove = "true";
}
