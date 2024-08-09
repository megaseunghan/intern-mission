package com.mission.intern.presentation.member;

import com.mission.intern.application.member.MemberService;
import com.mission.intern.presentation.member.dto.request.LoginRequest;
import com.mission.intern.presentation.member.dto.request.RegisterRequest;
import com.mission.intern.presentation.member.dto.response.RegisteredMemberInfo;
import com.mission.intern.presentation.member.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<RegisteredMemberInfo> register(@RequestBody RegisterRequest request) {
        RegisteredMemberInfo response = memberService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse response = memberService.login(request);
        return ResponseEntity.accepted().body(response);
    }
}
