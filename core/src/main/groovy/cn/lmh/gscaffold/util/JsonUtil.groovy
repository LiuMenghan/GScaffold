package cn.lmh.gscaffold.util

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
/**
 * JSON util use groovy.json.JsonOutput and groovy.json.JsonOutput
 * @author Liu Menghan
 *
 */
@CompileStatic
class JsonUtil {	
	public final static JsonSlurper parser = new JsonSlurper();
	
	/**
	 * Convert object to json string.
	 * @param o
	 * @return
	 */
	public static String obj2json(def o){
		return JsonOutput.toJson(o);
	}
	
	/**
	 * Convert string to object(map actually).
	 * @param json
	 * @return
	 */
	public static def json2obj(String json){
		return  parser.parseText(json);
	}
}
