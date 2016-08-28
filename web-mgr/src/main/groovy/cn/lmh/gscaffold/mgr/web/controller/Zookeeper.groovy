package cn.lmh.gscaffold.mgr.web.controller

import groovy.transform.CompileStatic

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import cn.lmh.gscaffold.mgr.service.ZookeeperService
import cn.lmh.gscaffold.mgr.web.param.ZookeeperPutParam
import cn.lmh.gscaffold.web.RestfulResponse

@RestController
@CompileStatic
@RequestMapping("/api/mgr/zookeeper/**")
class Zookeeper {
	Logger logger = LoggerFactory.getLogger(this.class);
	
	@Resource(name="service.zookeeper")
	ZookeeperService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public def get(HttpServletRequest request){
		String path = this.getPath(request);
		logger.debug(path);
		String data = service.get(path);
		return new ZookeeperGetResult(data : data);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public def put(@RequestBody ZookeeperPutParam param, HttpServletRequest request){
		String path = this.getPath(request);
		String data = param.data;
		service.set(path, data);
		return new RestfulResponse(){};
	}
	
	private String getPath(HttpServletRequest request){
		request.getRequestURI().substring('/api/mgr/zookeeper/'.length() - 1);
	}
}

class ZookeeperGetResult implements RestfulResponse{
	String data
}