package cn.lmh.gscaffold.mgr.service

import org.apache.zookeeper.data.Stat

trait ZookeeperService {
	abstract public void set(String path, String data);
	
	abstract public String get(String path);
	
	abstract public Stat exists(String path);
	
	abstract public void del(String path, boolean cascadable = false);
}
