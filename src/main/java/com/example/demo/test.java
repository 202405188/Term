package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;


@Controller
public class test {

    // 메인페이지
    @GetMapping("/")
    public String main() {
        return "main"; // Main.html 파일을 찾기
    }

    // shop 페이지
    @GetMapping("/shop")
    public String shop() {
        return "shop";   // Shop.html 찾기
    }

    // login 페이지
    @GetMapping("/login")
    public String login() {
        return "login";   // Login.html 찾기
    }

    // 회원가입 페이지
    @GetMapping("/membership")
    public String membership() {
        return "membership";   // Membership.html 찾기
    }

    @GetMapping("/knit1")
    public String knit1(){
        return "knit1";
    }

    @GetMapping("/knit")
    public String knit(){
        return "knit";
    }
    @GetMapping("/cart")
    public String cart(){
        return "cart";
    }

    /**
     * 아이디 중복 확인을 처리하는 API
     * @ResponseBody : HTML 페이지가 아닌 JSON/텍스트 데이터를 직접 반환
     * ResponseEntity : HTTP 상태 코드(성공/실패)와 데이터를 함께 반환
     */
    @PostMapping("/join/check-id")
    @ResponseBody 
    public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam("ID") String userId) {
        
        // (시뮬레이션) 실제로는 DB에서 아이디를 조회해야 합니다.
        // 여기서는 'admin' 또는 'test' 아이디만 이미 사용 중이라고 가정합니다.
        boolean isAvailable = !userId.equals("admin") && !userId.equals("test");

        // 아이디 길이가 5자 미만인 경우도 사용 불가 처리
        if (userId.length() < 5) {
            isAvailable = false;
        }

        // 결과를 JSON (Map) 형태로 반환
        // {"available": true} 또는 {"available": false}
        return new ResponseEntity<>(Map.of("available", isAvailable), HttpStatus.OK);
    }
    // 회원가입 폼 데이터를 받아서 처리하는 역할
    @PostMapping("/join")
    public String joinProcess(
            @RequestParam("ID") String userId,   // @RequeastParam : 프론트엔드(화면)에서 보낸 데이터를
            @RequestParam("PWD") String userPwd,  // 백엔드(서버) 코드의 변수에 연결해주는 역할.
            @RequestParam("name") String userName,
            @RequestParam("phone1") String phone1,
            @RequestParam("phone2") String phone2,
            @RequestParam("phone3") String phone3,
            @RequestParam("email") String userEmail,

            // 'name="agree_terms"' 값을 받음 required=true라서 체크를 안하면 오류남.
            @RequestParam("agree_terms") String agreeTerms,
            // 'name="agree_privacy"' 값을 받음
            @RequestParam("agree_privacy") String agreePrivacy,
            // 'name="agree_sms"' 값은 선택이므로, 없을 수도 있다. (required = false).
            @RequestParam(name = "agree_sms", required = false, defaultValue = "disagree") String agreeSms
)
{
        String userPhone = phone1 + "-" + phone2 + "-" + phone3;

        System.out.println("--- 회원가입 정보 ---");
        System.out.println("아이디: " + userId);
        System.out.println("비밀번호: " + userPwd);
        System.out.println("이름: " + userName);
        System.out.println("휴대폰번호: " + userPhone);
        System.out.println("이메일: " + userEmail);
        System.out.println("--------------------");
        System.out.println("이용약관 동의: " + agreeTerms);   // 체크하면 "on"
        System.out.println("개인정보 동의: " + agreePrivacy);   // 체크하면 "on"
        System.out.println("SMS 수신 동의: " + agreeSms);    // 체크하면 "on", 안하면 "disagree"
        System.out.println("--------------------");

        return "redirect:/login";   // 처리가 끝나고 /login 페이지로 이동하기
    }
}