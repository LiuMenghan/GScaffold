package cn.lmh.gscaffold.core.curator;

import groovy.transform.CompileStatic

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

import org.apache.curator.RetryPolicy
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.ExponentialBackoffRetry
import org.slf4j.Logger;
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.FactoryBean
import org.springframework.core.PriorityOrdered;
/**
 * 
 * @author Liu Menghan
 * {@link https://github.com/apache/curator/blob/master/curator-examples/src/main/java/framework/CreateClientExamples.java}
 *
 */
@CompileStatic
public class CuratorClientFactory implements FactoryBean<CuratorFramework>, PriorityOrdered {
	Logger logger = LoggerFactory.getLogger(this.class);
	int baseSleepTimeMs;
	int maxRetries;
	String connectionString;
	int connectionTimeoutMs;
	int sessionTimeoutMs;

	private CuratorFramework curator;
	
	@Override
	public int getOrder() {
		return Integer.MAX_VALUE - 1024;
	}
	
	@PostConstruct
	private void init(){
		if(null != this.curator){
			return;
		}
		// these are reasonable arguments for the ExponentialBackoffRetry. The first
		// retry will wait 1 second - the second will wait up to 2 seconds - the
		// third will wait up to 4 seconds.;
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries)
		
		this.curator = CuratorFrameworkFactory.builder()
			.connectString(connectionString)
			.retryPolicy(retryPolicy)
			.connectionTimeoutMs(connectionTimeoutMs)
			.sessionTimeoutMs(sessionTimeoutMs)
			// etc. etc.
			.build();
		
		this.curator.start();
		logger.debug("CuratorClient started");
	}
	
	@PreDestroy
	private void destroy(){
		this.curator.close();
	}
	@Override
	public Class<?> getObjectType() {
		return CuratorFramework.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	@Override
	public CuratorFramework getObject() throws Exception {	
		if(null == this.curator){
			this.init();
		}
		return this.curator
	}
}
