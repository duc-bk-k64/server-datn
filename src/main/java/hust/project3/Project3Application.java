package hust.project3;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@SpringBootApplication
@EnableWebMvc
@EnableCaching
@EnableScheduling
public class Project3Application {

	public static void main(String[] args) {
		SpringApplication.run(Project3Application.class, args);
	}

	@Bean
	public WebMvcConfigurer corConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://vuvanduc.id.vn","https://www.vuvanduc.id.vn","http://localhost:4200","http://vuanduc.id.vn","http://www.vuvanduc.id.vn").allowedMethods("*");
			}
		};
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		String hierarchy = "ROLE_ADMIN > ROLE_STAFF";
		roleHierarchy.setHierarchy(hierarchy);
		return roleHierarchy;
	}

	@Bean
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandlerhiearchy() {
		DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy());
		return expressionHandler;
	}
//	@Bean
//	NewTopic notification() {
//		return new NewTopic("notificationDATN",2,(short) 1);
//
//	}
//
//	@Bean
//	NewTopic feedback() {
//		return new NewTopic("feedbackDATN",2,(short) 1);
//
//	}
//
//	@Bean
//	NewTopic staff() {
//		return new NewTopic("notificationStaffDATN",2,(short) 1);
//
//	}
//
//	@Bean
//	JsonMessageConverter converter() {
//		return new JsonMessageConverter();
//	}

}
