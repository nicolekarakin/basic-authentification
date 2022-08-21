package org.nnn4.nfishe.basicauthentification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class BasicCtrl {
    @GetMapping
    public ResponseEntity<String> getGreeting(){
        Authentication auth= SecurityContextHolder
                .getContext()
                .getAuthentication();
        System.out.println("Authentication : "+auth+", auth.name : "+auth.getName());
        Object principal= auth.getPrincipal();
        System.out.println("principal class: "+principal.getClass());

//        if(principal instanceof UserDetails){
//            String name=((UserDetails) principal).getUsername();
//            return new ResponseEntity<>("Name: "+name, HttpStatus.OK);
//        }

        return new ResponseEntity<>("auth: "+auth, HttpStatus.OK);
    }
}
