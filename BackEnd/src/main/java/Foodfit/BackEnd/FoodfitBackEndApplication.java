package Foodfit.BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FoodfitBackEndApplication {
	public static void main(String[] args) {
		//http://localhost:8080/swagger-ui/index.html#
		SpringApplication.run(FoodfitBackEndApplication.class, args);
	}
}
