package org.nnn4.nfishe.basicauthentification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/greeter")
public class GreeterCtrl {

    @GetMapping("/login")
    String login() {
        return getGreeting().toString();
    }

    @GetMapping
    public ResponseEntity<String> getGreeting(){
        Authentication auth= SecurityContextHolder
                .getContext()
                .getAuthentication();
        System.out.println("Authentication : "+auth+", auth.name : "+auth.getName());
        Object principal= auth.getPrincipal();
        System.out.println("principal class: "+principal.getClass());

        if(principal instanceof UserDetails){
            String name=((UserDetails) principal).getUsername();
            return new ResponseEntity<>("Name: "+name, HttpStatus.OK);
        }
        String name=principal.toString();
        return new ResponseEntity<>("Name: "+name+", len: "+name.length(), HttpStatus.OK);
    }

    @GetMapping("/a")
    String getA(HttpSession session) {
        return "a";
    }

    @GetMapping("/a/a")
    String getA2(HttpSession session) {
        return "a2";
    }

    @GetMapping("logout")
    void logout(HttpSession session) {
        session.invalidate();
    }
}
