package com.jinzhun.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * Mapper父类，注意这个类不要让MP扫描到，这里可以定义一些公共方法
 *
 * @param <T>
 */
public interface SuperMapper<T> extends BaseMapper<T> {

}
