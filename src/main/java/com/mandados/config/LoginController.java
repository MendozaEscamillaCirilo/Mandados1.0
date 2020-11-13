package com.mandados.config;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

import com.mandados.Entidades.User;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller	
@RequestMapping	
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComercioRepository comerciorepository;

    @GetMapping("/login")
    public String loginPage() {	        
        return "login";	    
    }	

    @GetMapping("/home")
    public String userPage(Authentication authentication, Model model) {	   
        obtUsuario(model);
        return "home";	    
    }

    @GetMapping("/reset")
    public String resetPage() {
        return "resetcontrasenia";	    
    }

    public void obtUsuario(Model model){
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        model.addAttribute("usuario", user1);
        model.addAttribute("foto", "logos/"+user1.getUsername() + ".jpg");
        model.addAttribute("comercio", comerciorepository.findByEmail(user1.getUsername()));
    }
}
