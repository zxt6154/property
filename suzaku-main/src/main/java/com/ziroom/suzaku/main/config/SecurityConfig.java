package com.ziroom.suzaku.main.config;

/**
 * @author libingsi
 * @date 2021/10/11 20:02
 */
import com.ziroom.zcloud.sso.ZCloudSSOAuthenticationProvider;
import com.ziroom.zcloud.sso.ZCloudSSOTokenAuthenticationFilter;
import com.ziroom.zcloud.sso.jwt.JWT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Bean
    public ZCloudSSOTokenAuthenticationFilter zCloudSSOTokenAuthenticationFilter() throws Exception {
        return new ZCloudSSOTokenAuthenticationFilter(super.authenticationManagerBean());
    }

    @Bean
    public ZCloudSSOAuthenticationProvider zCloudSSOAuthenticationProvider() throws Exception {
        return new ZCloudSSOAuthenticationProvider(jwt());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决 swagger-ui.html 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决 doc.html 404 报错
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SuzakuInterceptor());
    }


    public JWT jwt() throws Exception {
        return JWT.useDefaultPublicKey();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 声明需要被私有云登陆拦截的 url
                .antMatchers("/**/*")
                .permitAll();

        http.authenticationProvider(zCloudSSOAuthenticationProvider())
                .csrf().disable()
                .exceptionHandling();

        http.addFilterBefore(zCloudSSOTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
