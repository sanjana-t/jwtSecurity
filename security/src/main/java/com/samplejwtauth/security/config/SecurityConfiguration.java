package com.samplejwtauth.security.config;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

	@Autowired
	private final JwtAuthenticationFilter jwtAuthFilter;
	
	private final AuthenticationProvider authenticationProvider;

	protected static final String[] HST_NAMES = {"https://167.71.234.110", "https://167.71.234.110:80",
            "http://localhost:5000",
            "http://localhost:8080",
            "http://20.124.7.196",
            "http://20.232.187.74",                               
            "http://localhost:3000",
            "http://discovery.course5i.com", "https://discovery.course5i.com",
            "http://mars.course5i.com", "https://mars.course5i.com",
            "https://143.110.185.170:80", "https://143.110.185.170", "https://20.72.165.129",
            "https://20.81.105.43", "https://20.232.114.3", "https://52.150.47.60",
            "https://20.228.243.148", "https://20.228.169.6", "https://40.87.46.245",
            "http://intelligentvmsvc.c5ailabs.com:8090", "https://intelligentvmsvc.c5ailabs.com:8090",
            "http://intelligentclustersvc.c5ailabs.com:8090",
            "https://intelligentclustersvc.c5ailabs.com:8090",
            "http://azurevm.c5ailabs.com", "https://azurevm.c5ailabs.com",
            "http://azurevm.c5ailabs.com:8080", "https://azurevm.c5ailabs.com:8080",
            "http://azurecl.c5ailabs.com", "https://azurecl.c5ailabs.com",
            "http://questionnairerepository.c5ailabs.com","https://questionnairerepository.c5ailabs.com",
            "http://questionnairerepository.c5ailabs.com:8088","https://questionnairerepository.c5ailabs.com:8088",
            "http://questionnairerepository.c5ailabs.com:9000","https://questionnairerepository.c5ailabs.com:9000"};
    
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String OPTIONS = "OPTIONS";
    public static final String WILD_CARD = "*";
    
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("api/v1/auth/register"," api/v1/auth/authenticate","/register");                
    }
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of(HST_NAMES));
            cors.setAllowedMethods(List.of(GET, POST, PUT, DELETE, OPTIONS));
            cors.setAllowedHeaders(List.of(WILD_CARD));
            return cors;
        })
		.and().csrf()
        .disable()
        .authorizeHttpRequests()
//        .authorizeRequests()
		.requestMatchers("/api/v1/auth/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html").permitAll()
//		.requestMatchers("api/v1/auth/","api/v1/auth/register","api/v1/auth/authenticate")
		.anyRequest().authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
		
			
		return http.build();
	}
}
