package com.mandados.config;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller	
@RequestMapping	
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {	        
        return "login";	    
    }

    @GetMapping("/login-error")
    public String loginerror(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/reset")
    public String resetPage() {
        return "resetcontrasenia";	    
    }
}
