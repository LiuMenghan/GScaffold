package service;

import groovy.transform.CompileStatic

import javax.annotation.Resource

import org.junit.Test;
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import cn.lmh.gscaffold.mgr.service.ZookeeperService

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@CompileStatic
public class ZookeeperServiceTest {
	@Resource(name="service.zookeeper")
	ZookeeperService service;
	
	String path = "/zookeeper/test";
	@Test
	public void test(){
		if(service.exists(this.path)){
			println service.get("/zookeeper/test")
			service.del(this.path, true);
		}
		
		assert null == service.exists(path);
		
		String data = "Test data in ${this.class.name}";
		service.set(path, data);
		
		assert null != service.exists(path);
		
		assert data == service.get(path);
		
		service.del(this.path);
		assert null == service.exists(path);		
	}
}
