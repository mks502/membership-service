package com.mks.membershipservice.security;

import com.mks.membershipservice.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션x
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크

                .antMatchers("/h2-console/**"
                        ,"/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()

                .antMatchers("/api/members").permitAll()    //회원가입
                .antMatchers("/api/login").permitAll()      //로그인
                .antMatchers("/oauth/**").permitAll()       //oauth 로그인 인증 필요 x
                .anyRequest().authenticated() // 그외 나머지 요청은 인증 받아야함
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider,redisUtil),
                        UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();
    }
}
