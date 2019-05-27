package com.sun.prms.config;

import com.sun.prms.security.*;
import com.sun.prms.security.jwt.*;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.annotation.PostConstruct;
import javax.naming.ldap.LdapContext;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService, TokenProvider tokenProvider, CorsFilter corsFilter, SecurityProblemSupport problemSupport) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .apply(securityConfigurerAdapter())
            .and().exceptionHandling().accessDeniedPage("/accessdenied");

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.ldapAuthentication()
//        	.userSearchBase("o=myO,ou=myOu") //don't add the base
//        	.userSearchFilter("(uid={0})")
//        	.groupSearchBase("ou=Groups") //don't add the base
//        	.groupSearchFilter("member={0}")
//        	.contextSource(getContextSource());
//    }
//    
//    @Bean
//    public BaseLdapPathContextSource getContextSource() {
//    	LdapContextSource contextSource = new LdapContextSource();
//        contextSource.setUrl("ldap://192.168.90.7");
//        contextSource.setBase("dc=mycompany,dc=com");
//        contextSource.setUserDn("cn=aUserUid,dc=mycompany,dc=com");
//        contextSource.setPassword("hisPassword");
//        contextSource.afterPropertiesSet(); //needed otherwise you will have a NullPointerException in spring
//
//        return (BaseLdapPathContextSource) contextSource;
//    }
    
    
   
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.ldapAuthentication()
//				.userDnPatterns("uid={0},ou=people")
//				.groupSearchBase("ou=groups")
//				.contextSource()
//					.url("ldap://192.168.90.7")
//					.and()
//				.passwordCompare()
//					.passwordEncoder(new LdapShaPasswordEncoder())
//					.passwordAttribute("userPassword");
//	}
    
   /* @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	   	System.out.println("$$$$$$$$$$$$$$$$$   In the configuration method");
   		// System.out.println("$$$$$$$$$$$$$$$$$  LDAP USER DN Pattern"+ ldapUserDnPattern);
   		//System.out.println("$$$$$$$$$$$$$$$$$  LDAP USER DN Pattern"+ true);
   		// System.out.println("$$$$$$$$$$$$$$$$$  LDAP USER DN Pattern"+ ldapPrincipalPassword);
   		//System.out.println("$$$$$$$$$$$$$$$$$  LDAP USER DN Pattern"+ ldapUrls);
   		//System.out.println("$$$$$$$$$$$$$$$$$  LDAP USER DN Pattern"+ ldapBaseDn);
   		auth
		.ldapAuthentication()
		.contextSource()
	   	.url("ldap://192.168.90.7:389/" + "dc=sti,dc=com")
//	   	.managerDn(ldapSecurityPrincipal)
//	   	.managerPassword(ldapPrincipalPassword)
	   	.and()
	   	.userDnPatterns("uid={0}");
        
        //System.out.println("test Envirnoment="+environment.getProperty("ldap.urls"));
    }*/

}
