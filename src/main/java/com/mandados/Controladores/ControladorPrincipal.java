package com.mandados.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping
public class ControladorPrincipal {
    @GetMapping("/resreg")
    public String principal(){
        return "/registro/restaurante";
    }
    
}
