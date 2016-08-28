package config

import groovy.transform.CompileStatic

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/place-holder.xml")
@CompileStatic
class PlaceHolderTest {
	
	@Value('${cn.lmh.test}')
	String testValue
	
	@Test
	public void test(){
		println(testValue);
	}
}
