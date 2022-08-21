package org.nnn4.nfishe.basicauthentification.securitynew;

import lombok.RequiredArgsConstructor;
import org.nnn4.nfishe.basicauthentification.account.Account;
import org.nnn4.nfishe.basicauthentification.account.AccountService;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Profile("new2")
@RequiredArgsConstructor
@Component
class UserDetailsServiceNewImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = accountService.findAccountByUsername(name)
                .orElse(null);
        if (account == null) {
            return null;
        }
        return account;
    }

}