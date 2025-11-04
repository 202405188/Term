// spring data jpa를 사용하여 user 객체의 데이터베이스 접근과 관리의 역할을 하는 자바 파일이다.
// db 저장 및 조회 기능을 제공하는 인터페이스.


package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// user 객체를 관리하며, 객체의 id 타입은 long임을 정의.
public interface UserRepository extends JpaRepository<User, Long> {

    // optional<User> : 해당 아이디 혹은 이메일을 가진 사용자가 존재할 수도, 안 할 수도 있음을 나타냄. (조회 결과 안전성)
    Optional<User> findByUsername(String username);   // 아이디로 조회 기능을 정의한 코드. (회원가입 시 아이디 중복 검사를 위해)
    Optional<User> findByEmail(String email);   // 이메일로 조회 기능을 정의한 코드. (회원가입 시 이메일 중복 검사를 위해)
}