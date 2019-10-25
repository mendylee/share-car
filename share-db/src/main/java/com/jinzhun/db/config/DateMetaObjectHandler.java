package com.jinzhun.db.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 自定义填充公共字段处理器
 */
public class DateMetaObjectHandler implements MetaObjectHandler {
	
	private final static String CREATE_TIME = "createTime";
    private final static String UPDATE_TIME = "updateTime";

	public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(CREATE_TIME, metaObject);
        Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
        if (createTime == null || updateTime == null) {
            Date date = new Date();
            if (createTime == null) {
                setFieldValByName(CREATE_TIME, date, metaObject);
            }
            if (updateTime == null) {
                setFieldValByName(UPDATE_TIME, date, metaObject);
            }
        }
	}

	public void updateFill(MetaObject metaObject) {
		setFieldValByName(UPDATE_TIME, new Date(), metaObject);
	}

}
