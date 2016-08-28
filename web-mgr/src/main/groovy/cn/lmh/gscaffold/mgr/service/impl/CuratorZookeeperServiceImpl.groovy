package cn.lmh.gscaffold.mgr.service.impl;

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import java.nio.charset.Charset
import javax.annotation.Resource

import org.apache.curator.framework.CuratorFramework
import org.springframework.stereotype.Service

import cn.lmh.gscaffold.mgr.service.ZookeeperService

import org.apache.zookeeper.data.Stat;

@CompileStatic(TypeCheckingMode.SKIP)
@Service("service.zookeeper")
public class CuratorZookeeperServiceImpl implements ZookeeperService {
	@Resource(name="curator")
	CuratorFramework curator; 
	Charset charset = Charset.forName("UTF-8");
	
	public void set(String path, String data){
		if(null == this.exists(path)){
			this.curator.create().forPath(path, data.getBytes(charset));
		}else{
			this.curator.setData().forPath(path, data.getBytes(charset));
		}
	}
	
	@Override
	public String get(String path){
		byte[] value = this.curator.getData().forPath(path);
		return new String(value, this.charset);
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
