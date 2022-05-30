package ru.mkonovalov.jurdoc.core.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.mkonovalov.jurdoc.core.router.Router;
import ru.mkonovalov.jurdoc.utils.jwt.AuthEntryPointJwt;
import ru.mkonovalov.jurdoc.utils.jwt.AuthTokenFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	private final AuthEntryPointJwt unauthorizedHandler;
	private final PasswordEncoder encoder;
	private static final String[] swagger = {
			// -- swagger ui
			"**/swagger-resources/**",
			"/v2/api-docs",
			"/webjars/**",
			"/configuration/ui",
			"/swagger-resources/**",
			"/configuration/security",
			"/swagger-ui/**",
			"/webjars/**"
	};

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers(anyMapping(Router.Authentication.ROOT)).permitAll()
			.antMatchers("/v2/api-docs",
					"/configuration/ui",
					"/swagger-resources/**",
					"/configuration/security",
					"/swagger-ui/**",
					"/swagger-ui/swagger-ui.html",
					"/webjars/**").permitAll()
			.anyRequest().permitAll();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	private String anyMapping(String mapping) {
		if (mapping.substring(mapping.length() -1).equals("/")) {
			return mapping + "**";
		}
		return mapping + "/**";
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(swagger);
	}
}
