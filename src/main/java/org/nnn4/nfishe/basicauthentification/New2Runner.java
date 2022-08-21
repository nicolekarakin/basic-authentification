package org.nnn4.nfishe.basicauthentification;

import lombok.RequiredArgsConstructor;
import org.nnn4.nfishe.basicauthentification.account.Account;
import org.nnn4.nfishe.basicauthentification.account.AccountRepo;
import org.nnn4.nfishe.basicauthentification.account.AccountService;
import org.nnn4.nfishe.basicauthentification.account.ERole;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@RequiredArgsConstructor
@Profile("new2")
public class New2Runner implements ApplicationRunner {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    private final AccountRepo accountRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("New2Runner=====================================" + accountService.count() + "");
        testMongoDb();
        System.out.println("New2Runner=====================================" + accountService.count() + "");
//
//        Account userTest = accountService.save(new Account(
//                passwordEncoder.encode("password"), "user",
//                Arrays.asList(new SimpleGrantedAuthority(ERole.ADMIN.name())), true
//        ));
//        System.out.println("userTest: " + userTest);
//        System.out.println("userTest: " + accountService.findAccountByUsername("user").get().getId());

    }


    private void testMongoDb() {
        accountService.findAccountByUsername("frank").orElseGet(() ->
                accountService.save(
                        new Account(
                                passwordEncoder.encode("frank123"),
                                "frank",
                                Arrays.asList(new SimpleGrantedAuthority(ERole.ADMIN.name())),
                                true
                        )));

        accountService.findAccountByUsername("anna").orElseGet(() ->
                accountService.save(
                        new Account(
                                passwordEncoder.encode("anna123"),
                                "anna",
                                Arrays.asList(new SimpleGrantedAuthority(ERole.BASIC.name())),
                                true
                        )));

        accountService.findAccountByUsername("annafrank").orElseGet(() ->
                accountService.save(
                        new Account(
                                passwordEncoder.encode("annafrank"),
                                "annafrank",
                                Arrays.asList(new SimpleGrantedAuthority(ERole.BASIC.name()),
                                        new SimpleGrantedAuthority(ERole.ADMIN.name())),
                                true
                        )));

        accountService.findAccountByUsername("user").orElseGet(() ->
                accountService.save(
                        new Account(
                                passwordEncoder.encode("password"),
                                "user",
                                Arrays.asList(new SimpleGrantedAuthority(ERole.ADMIN.name())),
                                true
                        )));
        Instant created = accountService.findAccountByUsername("user").get().getCreatedAt();
        System.out.println("user created: " + created);
    }

    public void getBirthsday() {
        var hasi = LocalDate.of(2022, 8, 12);
        var sec = SECONDS.between(
                Instant.now(),
                hasi.atStartOfDay().atZone(ZoneId.systemDefault())
        );
        System.out.println("Noch " + sec + " Sekunden");

        var myinstant = Instant.parse("1900-12-30T07:00:00Z");
        System.out.println("---------------------------------");
        System.out.println(myinstant);
        System.out.println(myinstant.atZone(ZoneId.of("Europe/Berlin")));
    }
}
//db.account.getIndexes()
//db.account.find({"username":"frank"})

//Caused by: com.mongodb.MongoWriteException: Write operation error on server localhost:27017.
//        Write error: WriteError{code=11000, message='E11000 duplicate key error collection: test.account index: username dup key: { username: "frank" }', details={}}.