package edu.djharo.minsait.msalumnosdjharo;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//1 método para definir roles- usuarios
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//declaramos roles en memoria
		auth.inMemoryAuthentication().withUser("user1").password("{noop}user1") // lo de {noop} se pone para no obligar a usar mecanismo de encriptación
		.roles("USER").and().withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
		
		/*
		 * lo siguiente sería para el caso de que quisiéramos encriptar la password:
		 * String pw1=new BCryptPasswordEncoder().encode("user1");
		 * System.out.println(pw1); auth .inMemoryAuthentication() .withUser("user1")
		 * .password("{bcrypt}"+pw1) //.password(pw1) .roles("USER") .and()
		 * .withUser("admin") .password(new BCryptPasswordEncoder().encode("admin"))
		 * .roles("USER", "ADMIN");
		 * 
		 * 
		 * /*la seguiente configuración será para el caso de usuarios en una base de
		 * datos auth.jdbcAuthentication().dataSource(datasourceSecurity)
		 * .usersByUsernameQuery("select username, password, enabled" +
		 * " from users where username=?")
		 * .authoritiesByUsernameQuery("select username, authority " +
		 * "from authorities where username=?");
		 */
	}
	
	//2 definimos las restricciones sobre los servicios
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		// solo los miembros del rol admin podrán borrar alumnos
		//MODIFICAMOS LA SEGURIDAD PARA QUE SÓLO PIDA autenticación en este caso 
		//.antMatchers(null)
		.antMatchers(HttpMethod.DELETE, "/alumnos/**").hasRole("ADMIN")
		.and().httpBasic();
		
		// con un *, se protege una dirección que venga con un elemento más. 
		//Con dos *,se protegen todas las direcciones, que vengan con cualquier número de datos adicionales o niguno
		//también puedo usar el nombre exacto pej: GET
		//.antMatchers("/api/alumnos/**").authenticated()//
	}

}