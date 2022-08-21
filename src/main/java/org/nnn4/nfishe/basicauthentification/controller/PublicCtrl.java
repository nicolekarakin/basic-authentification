package org.nnn4.nfishe.basicauthentification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("/api/public")
public class PublicCtrl {
    @GetMapping
    public ResponseEntity<String> getGreeting(){
        Authentication auth= SecurityContextHolder
                .getContext()
                .getAuthentication();
        System.out.println("Authentication : "+auth+", auth.name : "+auth.getName());
        Object name= auth.getPrincipal();
        System.out.println("principal class: "+name.getClass());
        return new ResponseEntity<>("Public Name: "+name.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/login")
    String login() {
        return getUsername();
    }

    @GetMapping(path = "/me")
    String getUsername() {
        return ((Principal)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getName();
    }

    @GetMapping(path = "/logout")
    void logout(HttpSession session) {
        session.invalidate();
    }
}
