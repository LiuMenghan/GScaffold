package cn.lmh.gscaffold.cache

import groovy.transform.CompileStatic
import cn.lmh.gscaffold.util.JsonUtil

@CompileStatic
trait CacheTrait {
	String prefix="";
	String suffix="";
	
	long cacheTimeSeconds = 60L;
	
	@Deprecated
	abstract public boolean directPut(String key, String value, long cacheTimeSeconds);
	
	@Deprecated
	abstract String directGet(String key);
	
	@Deprecated
	abstract boolean directRemove(String key);	
	
	public boolean put(String key, def value){
		return this.put(key, value, prefix, suffix)
	}
	
	public boolean put(String key, def value, String prefix){
		return this.put(key, value, prefix, suffix);
	}
	
	public boolean put(String key, def value, String prefix, String suffix){
		String _value = value instanceof String ?
			(String) value : JsonUtil.obj2json(value);
		return directPut("${prefix}${key}${suffix}", _value, cacheTimeSeconds);
		
	}
	
	public def get(String key){
		return get(key, prefix, suffix)
	}
	
	public def get(String key, String prefix){
		return get(key, prefix, suffix)
	}
	
	public def get(String key, String prefix, String suffix){
		String _value = directGet("${prefix}${key}${suffix}");
		try{
			return JsonUtil.json2obj(_value);
		}catch(Exception e){
			return _value
		}
	}
	
	public boolean remove(String key){
		return directRemove("${prefix}${key}${suffix}");
	}
	
	public boolean remove(String key, String prefix){
		return directRemove("${prefix}${key}${suffix}");
	}
	
	public boolean remove(String key, String prefix, String suffix){
		return directRemove("${prefix}${key}${suffix}");
	}
	
}
