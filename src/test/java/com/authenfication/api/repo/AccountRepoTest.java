package com.authenfication.api.repo;

import com.authenfication.api.application.domain.Account;
import com.authenfication.api.application.repository.AccountRepository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class AccountRepoTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByUid_thenReturnUser() {
        String accountId = "test2";
        String name = "angrydaddy";
        String email = "angrydaddy@gmail.com";
        // given
        accountRepository.save(Account.builder()
                .accountId(accountId)
                .password(passwordEncoder.encode("1234"))
                .name(name)
                .email(email)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        // when
        Optional<Account> user = accountRepository.findByAccountId(accountId);
        // then
        assertNotNull(user);// user객체가 null이 아닌지 체크
        assertTrue(user.isPresent()); // user객체가 존재여부 true/false 체크
        assertEquals(user.get().getName(), name); // user객체의 name과 name변수 값이 같은지 체크
        assertThat(user.get().getName(), is(name)); // user객체의 name과 name변수 값이 같은지 체크
    }
}
