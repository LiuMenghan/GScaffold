package cn.lmh.gscaffold.web

import groovy.transform.CompileStatic;
/**
 * Restful Service Response
 * @author Liu Menghan
 *
 */
@CompileStatic
trait Response {
	
	private static final int SUCCESS = 0;
	
	int errorCode = this.SUCCESS;
	
	String errorMsg;
}
