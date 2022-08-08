package org.nnn4.nfishe.basicauthentification.myuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicCtrl {
    @GetMapping
    public ResponseEntity<String> getGreeting(){
        String name= SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal().toString();
        return new ResponseEntity<>("Public Name: "+name+", len: "+name.length(), HttpStatus.OK);
    }
}
