package org.nnn4.nfishe.basicauthentification.securityold;

import lombok.RequiredArgsConstructor;
import org.nnn4.nfishe.basicauthentification.account.Account;
import org.nnn4.nfishe.basicauthentification.account.AccountService;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Profile("old2")
@RequiredArgsConstructor
@Component
class UserDetailsServiceOldImpl implements UserDetailsService {

    private final AccountService accountService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findAccountByUsername(username)
                .orElse(null);
        if (account == null) {
            return null;
        }
        return account;
    }

}