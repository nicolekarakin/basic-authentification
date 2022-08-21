package org.nnn4.nfishe.basicauthentification.account;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Profile({"new2", "old2"})
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepo accountRepo;

    public Account findAccountById(String id) {
        return accountRepo
                .findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account with id: "+id+" not found"));
    }

    public Optional<Account> findAccountByUsername(String name) {
        List<Account> accounts= accountRepo.findByUsername(name);
        if(accounts.size()==0)return Optional.empty();
        else if(accounts.size()==1)return Optional.of(accounts.get(0));
        else {
            String ids=accounts.stream().map(a->a.getId()).reduce("",(id,s) -> s+", "+id);
            throw new RuntimeException("username should be unique, but query returned: " +ids);
        }
    }

    public Account save(Account account) {
        if(accountRepo.findById(account.getId()).isEmpty())
        return accountRepo.save(account);
        else throw new RuntimeException("account with username: " +account.getUsername()+" already in db");
    }

    public Long count() {
        return accountRepo.count();
    }

}
