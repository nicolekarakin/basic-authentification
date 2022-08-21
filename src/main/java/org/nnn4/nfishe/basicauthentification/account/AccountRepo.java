package org.nnn4.nfishe.basicauthentification.account;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.List;

@Profile({"old2","new2"})
public interface AccountRepo extends MongoRepository<Account, String> {
//    Optional<Account> findByUsername(String username);
    List<Account> findByUsername(String username);

    @Query("{username:'?0'}")
    Account findUserByUsername(String username);

    @Query(value = "{}", fields = "{username : 1}")
    List<Account> findUsernameAndId();

    @Query(value = "{}", fields = "{_id : 0}")
    List<Account> findUsernameAndFullnameExcludeId();

    @Query("{ 'username' : ?0 }")
    List<Account> findAccountsByUsername(String username);

    @Query("{ 'createdAt' : { $gt: ?0, $lt: ?1 } }")
    List<Account> findAccountsByCreatedAtBetween(Instant ageGT, Instant ageLT);

    @Query("{ 'username' : { $regex: ?0 } }")
    List<Account> findAccountsByRegexpFullname(String regexp);

    List<Account> findByFullname(String fullname);


}
