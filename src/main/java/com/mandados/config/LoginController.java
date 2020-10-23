package com.mandados.config;
import org.springframework.stereotype.Controller;	
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller	
@RequestMapping	
public class LoginController {
    @GetMapping("/login")
    public String loginPage() {	        
        return "login";	    
    }	

    @GetMapping("/home")
    public String userPage(Authentication authentication) {	        
        return "home";	    
    }
}
