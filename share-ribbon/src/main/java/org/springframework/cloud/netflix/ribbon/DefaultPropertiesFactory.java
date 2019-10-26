package org.springframework.cloud.netflix.ribbon;

import static org.springframework.cloud.netflix.ribbon.SpringClientFactory.NAMESPACE;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;

/**
 * 默认的属性配置工厂扩展
 * 
 * <b>扩展 spring cloud ribbon的PropertiesFactory使其能够支持 配置全局的ribbon.NFLoadBalancerRuleClassName=package.YourRule然后各个微服务还可以根据自身情况做个性化定制。</b>
 * <b>如:SERVICE_ID.ribbon.NFLoadBalancerRuleClassName=package.YourRule</b>
 */
public class DefaultPropertiesFactory extends PropertiesFactory {
    
	@Autowired private Environment environment;

    private Map<Class<?>, String> classToProperty = new HashMap<>(5);

    public DefaultPropertiesFactory() {
        super();
        classToProperty.put(ILoadBalancer.class, "NFLoadBalancerClassName");
        classToProperty.put(IPing.class, "NFLoadBalancerPingClassName");
        classToProperty.put(IRule.class, "NFLoadBalancerRuleClassName");
        classToProperty.put(ServerList.class, "NIWSServerListClassName");
        classToProperty.put(ServerListFilter.class, "NIWSServerListFilterClassName");
    }

	/**
	 * 重写 支持 ribbon.NFLoadBalancerRuleClassName=package.YourRule 全局配置的方式
	 */
	@Override
	public String getClassName(Class clazz, String name) {
		String className = super.getClassName(clazz, name);
		// 读取全局配置
		if (!StringUtils.hasText(className) && this.classToProperty.containsKey(clazz)) {
			String classNameProperty = this.classToProperty.get(clazz);
			className = environment.getProperty(NAMESPACE + "." + classNameProperty);
		}
		return className;
	}
}
