package cn.lmh.gscaffold.util

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

@CompileStatic
class JsonUtil {
	public final static JsonBuilder builder = new JsonBuilder();
	
	public final static JsonSlurper parser = new JsonSlurper();
	
	public static String obj2json(def o){
		return builder.call(o.getProperties());
	}
	
	public static def json2obj(String json){
		return parser.parseText(json);
	}
}
