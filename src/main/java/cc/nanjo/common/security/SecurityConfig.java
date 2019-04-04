package cc.nanjo.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: xw
 * 2019-4-1 10:50
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin()
                //表单登录
                //.loginPage("/login") //用户未登录时的处理地址
                //.loginProcessingUrl("/authentication/login-service") //用户登录
                .and()
                .authorizeRequests()
                .antMatchers("/public/**/**", "/js*", "/css/*", "/post/*", "/res/**/*.*") //不拦截的URL
                .permitAll()
                .anyRequest()
                .authenticated();
    }

}