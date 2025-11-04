// spring 애플리케이션 내에서 비밀번호 암호화 기능을 사용할 수 있도록 해주는 자바 파일.
// BCryptPasswordEncoder 객체를 생성하여 passwordEncoder 타입의 bean으로 spring 컨테이너에 등록한다.


/*
BCryptPasswordEncder : spirng security에서 제공하며, 사용자가 회원가입 시 입력한 비밀번호를 안전하게 데이터베이스에 저장할 수
있도록 변환해주는 도구.
 */


package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.http.HttpMethod;


@Configuration
public class SecurityConfig {


    // @Bean : 이 메서드가 반환하는 객체를 spring 컨테이너에 등록해서 애플리케이션 전체에서 재사용할 수 있도록 함.
    // BCryptPasswordEncoder 객체를 Spring 컨테이너에 등록 (비밀번호 암호화 도구)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http
            // CSRF 보호 기능 비활성화 (h2 콘솔의 정상적인 작동을 위해 일단 비활성화)
            .csrf(csrf -> csrf.disable())

            // 접근 권한 설정 (인증 없이 접근 가능한 경로 설정)
            .authorizeHttpRequests(auth -> auth
                // 회원가입 페이지와 처리 URL
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/membership"), // 회원가입 폼
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/login", HttpMethod.POST.name()),
                    new AntPathRequestMatcher("/join/**"), // 회원가입 처리 및 아이디 중복 확인 등
                    new AntPathRequestMatcher("/css/**"), // CSS 파일 허용
                    new AntPathRequestMatcher("/js/**"), // JavaScript 파일 허용
                    new AntPathRequestMatcher("/images/**"), // 이미지 파일 허용
                    new AntPathRequestMatcher("/h2-console/**") // H2 콘솔 접근 허용
                ).permitAll()    // permitAll() : 모두 허용
                

                // 나머지 모든 요청은 인증된 사용자(로그인한 사용자)만 접근 허용
                .anyRequest().authenticated()   // authenticated() : 인증된 사람만 통과
            )
            
            // 로그인 설정
            .formLogin(form -> form
                .loginPage("/login") // 사용자 정의 로그인 페이지 URL
                .defaultSuccessUrl("/", true) // 로그인 성공 후 이동할 기본 페이지
                // 로그인 실패 시 이동할 URL
                .failureUrl("/login?error=true") 
                .permitAll()
            )
            
            // H2 콘솔 프레임 허용
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}