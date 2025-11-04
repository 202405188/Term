package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/") 
public class UserController {

// UserService 주입
 private final UserService userService;

public UserController(UserService userService) {
 this.userService = userService;
}

// 페이지 이동 맵핑
 @GetMapping("/") public String main() { return "main"; }
@GetMapping("/shop") public String shop() { return "shop"; }
 @GetMapping("/login") public String login() { return "login"; }
 @GetMapping("/knit1") public String knit1(){ return "knit1"; }
 @GetMapping("/knit") public String knit(){ return "knit"; }
@GetMapping("/cart") public String cart(){ return "cart"; }



 // 회원가입 페이지 요청 (GET /membership)
 @GetMapping("/membership")
 public String showRegistrationForm(Model model) {
 model.addAttribute("user", new User()); // User 객체를 폼에 바인딩하기 위해 추가
return "membership";
 }


// 회원가입 정보 제출 처리 (POST /join)
 @PostMapping("/join")
 public String joinProcess(@ModelAttribute("user") User user, Model model) {

try {
 userService.registerNewUser(user);

 // 성공 시, 로그인 페이지로 리다이렉트
return "redirect:/login"; 

 } catch (IllegalStateException e) {
 // 중복 등의 오류 발생 시, 오류 메시지를 모델에 담아 폼으로 다시 보낸다.
 model.addAttribute("errorMessage", e.getMessage());
return "membership"; // membership.html 폼으로 돌아감
}
 }



@PostMapping("/join/check-id")
@ResponseBody 

// **수정된 부분:** @RequestParam("username") 대신 @RequestParam("ID")로 변경합니다.
public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam("ID") String username) { 
 boolean isAvailable = userService.isUsernameAvailable(username); 
 
 // isAvailable 결과를 JSON으로 반환
return new ResponseEntity<>(Map.of("available", isAvailable), HttpStatus.OK);
    }
}