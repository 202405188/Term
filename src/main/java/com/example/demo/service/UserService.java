// spring data jpa를 사용하여 user 객체의 데이터베이스 접근과 관리의 역할을 하는 자바 파일이다.
// db 저장 및 조회 기능을 제공하는 인터페이스.

package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional; 
import java.util.Collections;


// UserDetailsService 인터페이스를 구현 (로그인 인증에 필요)
@Service
public class UserService implements UserDetailsService { 

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 생성자 주입
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 처리 메서드
    public void registerNewUser(User user) {
        // 아이디 중복 확인
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        
        //  이메일 중복 확인
        if (userRepository.findByEmail(user.getEmail()).isPresent()) { 
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    
    
    /**
     * 특정 사용자 이름(username)이 데이터베이스에 사용 가능한지 확인
     * @param username 확인할 사용자 이름
     * @return true: 사용 가능 (DB에 없음), false: 사용 불가능 (DB에 이미 있음)
     */
    public boolean isUsernameAvailable(String username) {
        // findByUsername 결과가 비어있다면 (isEmpty()), 사용 가능하다(true)는 의미
        return userRepository.findByUsername(username).isEmpty(); 
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // UserRepository를 사용하여 DB에서 사용자 이름으로 사용자를 찾는다.
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        // 찾은 User 객체의 정보를 Spring Security가 이해할 수 있는 UserDetails 객체로 변환한다.
        // Spring Security의 기본 User 객체는 사용자 이름, 암호화된 비밀번호, 권한 목록을 포함합니다.
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),      // 사용자 이름
            user.getPassword(),      // DB에 저장된 암호화된 비밀번호
            Collections.emptyList()  // 권한 정보
        );
    }
}