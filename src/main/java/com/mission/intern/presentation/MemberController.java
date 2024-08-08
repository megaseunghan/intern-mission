package com.mission.intern.presentation;

import com.mission.intern.application.member.MemberService;
import com.mission.intern.presentation.member.dto.request.RegisterRequest;
import com.mission.intern.presentation.member.dto.response.RegisteredMemberInfo;
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
}
