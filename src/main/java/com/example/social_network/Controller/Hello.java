package com.example.social_network.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/hello")
    public String Helo(){
        return "Xin Chao Viet Nam";
    }
}
