package com.wnmap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wnmap.bean.Msg;
import com.wnmap.service.CustomUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/api/login").loginProcessingUrl("/api/login").permitAll()
        .failureHandler(new AuthenticationFailureHandler() {
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				response.setContentType("application/json;charset=utf-8");
						Msg msg = new Msg(false, "登录失败");
		                ObjectMapper om = new ObjectMapper();
		                PrintWriter out = response.getWriter();
		                out.write(om.writeValueAsString(msg));
		                out.flush();
		                out.close();
			}
		})
        .successHandler(new AuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
						response.setContentType("application/json;charset=utf-8");
						Msg msg = new Msg(true, "登录成功");
                        ObjectMapper om = new ObjectMapper();
		                PrintWriter out = response.getWriter();
                        out.write(om.writeValueAsString(msg));
		                out.flush();
		                out.close();
			}
		})
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/api/logout").permitAll()
        .logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {

            	resp.setContentType("application/json;charset=utf-8");
				Msg msg = new Msg(true, "退出成功");
                ObjectMapper om = new ObjectMapper();
                PrintWriter out = resp.getWriter();
                out.write(om.writeValueAsString(msg));
                out.flush();
                out.close();
            }
        })
        .permitAll();
  	http.csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringAntMatchers("/h2-console/**");
	    http.headers().frameOptions().sameOrigin();
    	
//        http
//	        .authorizeRequests()
//	            .antMatchers("/css/*","/h2-console/**").permitAll()	// "/", "/home", 
//	            .anyRequest().authenticated()
//	            .and()
//	        .formLogin()
//	            .loginPage("/login")
//                .defaultSuccessUrl("/hello")
//	            .permitAll()
//	            .and()
//            .rememberMe()
//            //设置cookie有效期
////            .tokenValiditySeconds(60 * 60 * 24 * 7)
//            .and()
//            .logout()
//	            .permitAll();
////	    http.csrf().ignoringAntMatchers("/h2-console/**");
//	    http.csrf()
//        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//        .ignoringAntMatchers("/h2-console/**");
//	    http.headers().frameOptions().sameOrigin();
    }

    @Autowired
    private CustomUserService customUserService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService)
        .passwordEncoder(new CustomPasswordEncoder());
    }
    @Override

    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/index.html","/favicon.ico","/manifest.json","/logo192.png","/logo512.png","/static/**");

    }
}