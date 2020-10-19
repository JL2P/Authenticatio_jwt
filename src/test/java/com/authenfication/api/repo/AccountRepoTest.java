package com.authenfication.api.repo;

import com.authenfication.api.application.domain.Account;
import com.authenfication.api.application.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepoTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByAccountId_thenReturnAccount(){
        String accountId = "local";
        String name = "hellon";
        String email = "local@naver.com";

        //given
        accountRepository.save(Account.builder()
            .accountId(accountId)
                .password(passwordEncoder.encode("1234"))
                .name(name)
                .email(email)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        //when
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        //then
        assertNotNull(account);
        assertTrue(account.isPresent());

        assertEquals(account.get().getName(),name);
//        assertThat(account.get().getName(),is(name));

    }
}

