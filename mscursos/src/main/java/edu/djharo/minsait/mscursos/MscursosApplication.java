package edu.djharo.minsait.mscursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"edu.djharo.minsait.mscomun.model.entity", "edu.djharo.minsait.mscursos.model.entity"})
public class MscursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscursosApplication.class, args);
	}

}
