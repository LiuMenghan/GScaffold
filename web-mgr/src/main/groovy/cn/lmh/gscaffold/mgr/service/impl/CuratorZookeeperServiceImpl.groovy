package cn.lmh.gscaffold.mgr.service.impl;

import groovy.transform.CompileStatic

import javax.annotation.Resource

import org.apache.curator.framework.CuratorFramework
import org.springframework.stereotype.Service

import cn.lmh.gscaffold.mgr.service.ZookeeperService

import org.apache.zookeeper.data.Stat;

@CompileStatic
@Service("service.zookeeper")
public class CuratorZookeeperServiceImpl implements ZookeeperService {
	@Resource(name="curator")
	CuratorFramework curator; 
	String charset = "UTF-8";
	
	public void set(String path, String data){
		if(null == this.exists(path)){
			this.curator.create().forPath(path, data.getBytes(charset));
		}else{
			this.curator.setData().forPath(path, data.getBytes(charset));
		}
	}
	
	@Override
	public String get(String path){
		return new String(this.curator.getData().forPath(path), this.charset);
	}

	@Override
	public Stat exists(String path) {
		return this.curator.checkExists().forPath(path);
	}

	@Override
	public void del(String path, boolean cascadable) {
		def deleteBuilder = this.curator.delete();
		if(cascadable){
			deleteBuilder.deletingChildrenIfNeeded()
		}
		deleteBuilder.forPath(path);
	}
}
