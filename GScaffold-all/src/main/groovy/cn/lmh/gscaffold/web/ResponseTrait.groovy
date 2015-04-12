package cn.lmh.gscaffold.web

import groovy.transform.CompileStatic;

@CompileStatic
trait ResponseTrait {
	
	private static final int SUCCESS = 0;
	
	int errorCode = this.SUCCESS;
	
	String errorMsg;
}
