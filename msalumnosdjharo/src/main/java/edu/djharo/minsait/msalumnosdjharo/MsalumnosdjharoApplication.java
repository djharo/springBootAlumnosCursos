package edu.djharo.minsait.msalumnosdjharo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableFeignClients
@EnableEurekaClient
@EnableWebMvc
@SpringBootApplication
@EntityScan({"edu.djharo.minsait.mscomun.model.entity", "edu.djharo.minsait.msalumnosdjharo.model.entity"})
public class MsalumnosdjharoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosdjharoApplication.class, args);
	}
	
	//qué queremos documentar DOCKET
	public Docket buildDecket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("edu.djharo.minsait.msalumnosdjharo.controller"))
				.paths(PathSelectors.any()).build();
	}
	
	@Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  registry.addResourceHandler("swagger-ui.html")
	    .addResourceLocations("classpath:/META-INF/resources/");
	  
	  registry.addResourceHandler("/webjars/**")
	    .addResourceLocations("classpath:/META-INF/resources/webjars/");
	  
	 }
}
