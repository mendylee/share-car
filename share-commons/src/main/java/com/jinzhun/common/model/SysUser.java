package com.jinzhun.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUser extends SuperEntity {
	private static final long serialVersionUID = -5886012896705137070L;
	
	private int role;
	private String username;
	private String password;
	private String nickname;
	@TableField("head_img_url")
	private String headImgUrl;
	private String mobile;
	private Integer sex;
	private Boolean enabled;
	private String address;
	@TableField("open_id")
	private String openId;
	@TableLogic
	@TableField("is_del")
	private boolean isDel;
}
