package com.authenfication.api.application.web;

import com.authenfication.api.application.config.security.JwtTokenProvider;
import com.authenfication.api.application.domain.Account;
import com.authenfication.api.application.exception.CEmailSigninFailedException;
import com.authenfication.api.application.repository.AccountRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1")
public class SignController {
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public String signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                         @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {

        Account acocunt = accountRepository.findByAccountId(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, acocunt.getPassword()))
            throw new CEmailSigninFailedException();

        return jwtTokenProvider.createToken(String.valueOf(acocunt.getMsrl()), acocunt.getRoles());

    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public String signup(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String accountId,
                         @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                         @ApiParam(value = "이메일", required = true) @RequestParam String email,
                         @ApiParam(value = "이름", required = true) @RequestParam String name) {

        accountRepository.save(Account.builder()
                .accountId(accountId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());


        return "200";
    }



}
