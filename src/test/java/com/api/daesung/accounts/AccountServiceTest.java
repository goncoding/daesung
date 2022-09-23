//package com.api.daesung.accounts;
//
//import com.api.daesung.common.RestDocsConfiguration;
//import org.assertj.core.api.Assertions;
//import org.hamcrest.Matchers;
//import org.junit.Ignore;
//import org.junit.Rule;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.rules.ExpectedException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@ActiveProfiles("test")
//class AccountServiceTest{
//
//    @Rule
//    public ExpectedException expectedException  = ExpectedException.none();
//
//    @Autowired
//    AccountService accountService;
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Test
//    public void findByUsername() throws Exception{
//        //given
//        String username = "keesun@email.com";
//        String password = "keesun";
//        Account account = Account.builder()
//                .email(username)
//                .password(password)
//                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//                .build();
//
//        accountRepository.save(account);
//
//        //when
//        UserDetailsService userDetailsService = (UserDetailsService) accountService;
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        //then
//        assertThat(userDetails.getPassword()).isEqualTo(password);
//
//    }
//
//
//    @Test()
//    public void findByUsernameFail() throws Exception{
//        //expected
//        String username = "random@email.com";
//        accountService.loadUserByUsername(username);
//        expectedException .expect(UsernameNotFoundException.class);
//        expectedException .expectMessage(Matchers.containsString(username));
//
//        //when
//
//    }
//
//
//}