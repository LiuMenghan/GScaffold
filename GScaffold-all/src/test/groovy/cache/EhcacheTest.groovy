package cache

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

import javax.annotation.Resource

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import cn.lmh.gscaffold.cache.Cache

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:cache/ehcache.xml")
@CompileStatic
class EhcacheTest {
	
	@Resource(name="gscalffold.cache.ehcache")
	Cache cache;
	
	@Test
	public void testTimeout(){
		User user = new User(username : "lmh", password : "1234");
		User anotherUser =  new User(username : "lmh", password : "1234");
		cache.put("user", user);
		User cacheUser = cache.get("user") as User;
		assert user == cacheUser
		Thread.sleep(31_000);
		cacheUser = cache.get("user") as User;
		assert null == cacheUser;
	}
}
	
@EqualsAndHashCode
class User implements Serializable{
	String username;
	String password;

}
