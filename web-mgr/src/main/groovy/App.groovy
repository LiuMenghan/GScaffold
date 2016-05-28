import groovy.transform.CompileStatic

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.context.annotation.ImportResource

@CompileStatic
@EnableAutoConfiguration(exclude=[HibernateJpaAutoConfiguration.class])
@ImportResource("classpath:applicationContext.xml")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
