package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;   // lombok : 코드를 간결하게 만들어주는 라이브러리
import lombok.Setter;

@Entity // 이 클래스가 DB 테이블과 연결됨을 의미
@Table(name = "T_USER")  // 테이블 이름을 'user'로 지정
@Getter
@Setter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username; // 아이디

    @Column(nullable = false, length = 100)
    private String password; // 해싱된 비밀번호 저장

    @Column(nullable = false, length = 50)
    private String name; // 이름

    @Column(name = "phone_number", unique = true, length = 20)
    private String phoneNumber; // 전화번호

    @Column(unique = true, nullable = false, length = 100)
    private String email; // 이메일

    // 기본 생성자 (JPA 필수)
    public User() {}

}