package com.example.authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private static String REALM="TEST_REALM";

	private static final int THIRTY_DAYS = 60 * 60 * 24 * 30; 
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private UserApprovalHandler userApprovalHandler;
 
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private OAuthClientProperties oAuthClientProperties;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	@ConditionalOnProperty(prefix="security.oauth2.client",name="storeType" ,havingValue="inMemory", matchIfMissing=true)
	public InMemoryClientDetailsService inMemoryClientDetailsService(){
		InMemoryClientDetailsService clientDetailsService = new InMemoryClientDetailsService();
		Map<String, ClientDetails> clientDetailsStore = new HashMap();
		oAuthClientProperties.getAppClients().forEach(appClient -> {
			BaseClientDetails clientDetails = new BaseClientDetails();
			clientDetails.setClientId(appClient.getClientId());
			clientDetails.setClientSecret(passwordEncoder.encode(appClient.getClientSecret()));
			clientDetails.setScope(new ArrayList(appClient.getScopes()));
			clientDetails.setAuthorizedGrantTypes(new ArrayList(appClient.authorizedGrantTypes));
			clientDetails.setAccessTokenValiditySeconds(3600);
			clientDetailsStore.put(clientDetails.getClientId(),clientDetails);
			clientDetailsService.setClientDetailsStore(clientDetailsStore);
		});
		return clientDetailsService;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(inMemoryClientDetailsService());
	}
 
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
		.authenticationManager(authenticationManager);
	}
 
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM);
		oauthServer.checkTokenAccess("permitAll()"); 
	}

}


