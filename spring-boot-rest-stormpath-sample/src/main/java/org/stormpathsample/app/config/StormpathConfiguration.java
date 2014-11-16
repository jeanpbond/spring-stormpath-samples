package org.stormpathsample.app.config;

import java.net.URL;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.google.common.collect.Sets;
import com.stormpath.spring.security.client.ClientFactory;
import com.stormpath.spring.security.provider.DefaultGroupGrantedAuthorityResolver;
import com.stormpath.spring.security.provider.DefaultGroupGrantedAuthorityResolver.Mode;
import com.stormpath.spring.security.provider.StormpathAuthenticationProvider;

@Configuration
@EnableWebMvcSecurity
public class StormpathConfiguration extends WebSecurityConfigurerAdapter {
	
    @Value("${stormpath.api.properties}")
    private String stormPathApiKeyProperties;
    
    @Value("${stormpath.application.rest.url}")
    private String applicationRestUrl;
    
    @Autowired
    private CacheManager cacheManager;
    
    @Autowired
    private AuthenticationProvider authenticationProvider;
	
    @Bean
    @Autowired
    public StormpathAuthenticationProvider stormpathAuthenticationProvider(ClientFactory clientFactory) throws Exception {
    	StormpathAuthenticationProvider provider = new StormpathAuthenticationProvider();
    	provider.setGroupGrantedAuthorityResolver(defaultGroupGrantedAuthorityResolver());
    	provider.setApplicationRestUrl(applicationRestUrl);
    	provider.setClient(clientFactory.getObject());
    	return provider;
    }
	
    @Bean
    public ClientFactory clientFactory() {
    	ClientFactory clientFactory = new ClientFactory();
    	URL url = this.getClass().getClassLoader().getResource(stormPathApiKeyProperties);
    	clientFactory.setApiKeyFileLocation(url.getPath());
    	clientFactory.setCacheManager(cacheManager);
    	return clientFactory;
    }
	
    @Bean
    public DefaultGroupGrantedAuthorityResolver defaultGroupGrantedAuthorityResolver() {
    	DefaultGroupGrantedAuthorityResolver authorityResolver = new DefaultGroupGrantedAuthorityResolver();
    	Set<DefaultGroupGrantedAuthorityResolver.Mode> nodes = Sets.newHashSet(Mode.NAME);
    	authorityResolver.setModes(nodes);
    	return authorityResolver;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {		
    	http
    	  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	  .and()
    	  .httpBasic()
    	  .and()
    	  .authorizeRequests()
    	    .antMatchers("/**")
    	      .authenticated();
    }

}
