package org.nnn4.nfishe.basicauthentification.myuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
        Object principal=SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if(principal instanceof User){
            String name=((User) principal).getUsername();
            return new ResponseEntity<>("Name: "+name+", len: "+name.length(), HttpStatus.OK);
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
