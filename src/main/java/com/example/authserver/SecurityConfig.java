package com.example.authserver;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private InMemoryUserDetailsService inMemoryUserDetailsService;

	@Autowired
	private CustomAuthenticationProvider authenticationProvider;

	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inMemoryUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
	public void configure(WebSecurity web) {
		web.debug(false);
	}

    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Map<String, UserEntity> userMap() {
		Map users = new HashMap<String, UserEntity>();
		users.put("admin", UserEntity.builder()
				.username("admin")
				.password(passwordEncoder().encode("password"))
				.roles(Arrays.asList("ADMIN","MANAGER"))
				.build());
		users.put("terry", UserEntity.builder()
				.username("terry")
				.password(passwordEncoder().encode("password"))
				.roles(Arrays.asList("DEVELOPER"))
				.build());
		users.put("tommy", UserEntity.builder()
				.username("tommy")
				.password(passwordEncoder().encode("password"))
				.roles(Arrays.asList("MASTER"))
				.build());
		return users;
	}
 
    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected void configure(HttpSecurity http) throws Exception {
		http
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.csrf().disable()
	  	.authorizeRequests()
	  	.antMatchers("/oauth/token").permitAll()
	  	.antMatchers("/check_token").permitAll()
        .anyRequest().authenticated()
	  	.and()
	  	.httpBasic()
	  		.realmName("CRM_REALM");
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
 
	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}
	
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
}