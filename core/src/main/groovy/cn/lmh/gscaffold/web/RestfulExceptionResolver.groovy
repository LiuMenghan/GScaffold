package cn.lmh.gscaffold.web

import javax.net.ssl.SSLEngineResult.Status;

import groovy.transform.CompileStatic

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler

@CompileStatic
class RestfulExceptionResolver{
	@ExceptionHandler(Exception.class)
	public RestfulResponse resolveException(Exception e){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		return new SimpleRestfulResponse(
			errno : status.value,
			errmsg : [
				"message" : status.reasonPhrase,
				"detail" : this.getErrInfo(e)	
			]
		//The follow brace pair are unnecessary and can be compiled by groovy compiler,
		//but they are still added to avoid announcing as a in Eclipse.
		){};
	
	}
	
	private String getErrInfo(Exception e){
		StringWriter sw = new StringWriter();  
        PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		return sw.toString();
	};
}

