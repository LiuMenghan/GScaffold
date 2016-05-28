package cn.lmh.gscaffold.web

import groovy.transform.CompileStatic
import cn.lmh.gscaffold.util.Errno
/**
 * Restful Service Response
 * @author Liu Menghan
 *
 */
@CompileStatic
trait RestfulResponse{
	
	int errno = Errno.SUCCESS;
	
	def errmsg;
}

@CompileStatic
class SimpleRestfulResponse implements RestfulResponse{

}
