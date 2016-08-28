package cn.lmh.gscaffold.config

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import java.nio.charset.Charset
import java.util.Properties;

import org.apache.commons.io.IOUtils
import org.apache.curator.framework.CuratorFramework
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanDefinitionStoreException
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.BeanDefinitionVisitor
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer.PropertyPlaceholderConfigurerResolver;
import org.springframework.core.PriorityOrdered
import org.springframework.util.DefaultPropertiesPersister
import org.springframework.util.ObjectUtils
import org.springframework.util.PropertiesPersister
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;

@CompileStatic(TypeCheckingMode.SKIP)
class ZookeeperPlaceHolder extends PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor, PriorityOrdered {
	Logger logger = LoggerFactory.getLogger(this.class);
	CuratorFramework curator;
	List<String> paths;
	Map<String, String> props = new Properties();
	String beanName;
	BeanFactory beanFactory;
	
	@Override
	public int getOrder() {
		return Integer.MAX_VALUE - 1024 * 2;
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		paths.each { path -> 
			byte[] value = this.curator.getData().forPath(path);
			ByteArrayInputStream bais = new ByteArrayInputStream(value);
			BufferedInputStream bis = new BufferedInputStream(bais);
			try{
				Properties props = new Properties();
				props.load(bis);
				props
				this.convertProperties(props);
				this.processProperties(beanFactory, props);
			}catch(Exception e){
				throw e
			}finally{
				IOUtils.closeQuietly(bais);
				IOUtils.closeQuietly(bis);
			}
		}
		logger.debug("ZookeeperPlaceHolder started.");
		
	}
			
}
