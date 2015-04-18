package cn.lmh.gscaffold.cache.impl

import groovy.transform.CompileStatic
import net.sf.ehcache.Cache
import net.sf.ehcache.CacheManager
import net.sf.ehcache.Element
import net.sf.ehcache.config.CacheConfiguration

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.InitializingBean
/**
 * Implement cache interface with Ehcahce.
 * @author Liu Menghan
 *
 */
@CompileStatic
class EhcacheImpl implements InitializingBean,FactoryBean{
	private static final Logger logger = LogManager.getLogger(EhcacheImpl.class);
	
	private static final CacheManager cacheManager = CacheManager.newInstance();
	
	Map<String, String> props
	
	private CacheConfiguration config;
	
	public void setProps(Map<String, String> props){
		this.config = new CacheConfiguration();
		props.each {it ->
			if(config.hasProperty(it.key)){
				String valuePlain = props[it.key];
				if(valuePlain.isLong()){
					config[it.key] = valuePlain.toLong();
				}else{
					config[it.key] = valuePlain;
				}
			}			
		}
	}
	private EhcacheClient client;
	
	String prefix;
	String suffix;

	@Override
	public void afterPropertiesSet() throws Exception {
		Cache cache = new Cache(this.config);
		cacheManager.addCache(cache);	
		this.client = new EhcacheClient(cache : cache, prefix:prefix, suffix:suffix);	
	}

	@Override
	public Object getObject() throws Exception {
		assert client != null;
		return this.client;
	}

	@Override
	public Class getObjectType() {
		return cn.lmh.gscaffold.cache.Cache.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	static class EhcacheClient implements cn.lmh.gscaffold.cache.Cache{
		Cache cache;
	
		@Override
		public boolean directPut(String key, String value) {
			try{
				Element ele = new Element(key, value);
				cache.put(ele);
				return true;
			}catch(Exception e){
				logger.error(e);
				return false;
			}
		}
	
		@Override
		public String directGet(String key) {
			Element ele = cache.get(key);
			return ele ? ele.value : null;
		}
	
		@Override
		public boolean directRemove(String key) {
			return cache.remove(key);
		}
	}
	
}
