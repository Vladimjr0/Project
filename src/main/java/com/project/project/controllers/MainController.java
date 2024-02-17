package com.project.project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/unsecured")
    public String unsecuredData(){
        return ("Unsecured Data");
    }

    @GetMapping("/secured")
    public String securedData(){
        return ("Secured Data");
    }

    @GetMapping("/owner")
    public String ownerData(){
        return ("Owner Data");
    }

    @GetMapping("/info")
    public String infoData(Principal principal){
        return principal.getName();
    }


}
