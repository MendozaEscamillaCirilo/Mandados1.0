package com.mandados.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;

import com.mandados.Entidades.Authority;
import com.mandados.Entidades.Restaurante;
import com.mandados.Entidades.User;
// import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Restaurante.IRestauranteService;
import com.mandados.User.IUserService;
// import com.mandados.Repository.AuthorityRepository;
import com.mandados.config.Passgenerator;
// import com.mandados.ReposithoUserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import  java.util.Random;

// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;
import com.mandados.Email.EmailService;

@Controller
@RequestMapping
public class ControladorPrincipal {

    @Autowired
    private IRestauranteService servicerestaurante;

    @Autowired
    private IUserService serviceuser;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private AuthorityRepository authorityRepository;
    
    @GetMapping("/resreg")
    public String principal(Model model){
    	model.addAttribute("userrestaurante", new Restaurante());
        return "registro/restaurante";
    }

    @PostMapping("/register_restaurante")
	public String save(@Validated Restaurante r, Model model){
        servicerestaurante.save(r);
        User newuserrestaurante = new User();
        newuserrestaurante.setUsername(r.getCorreo());
        newuserrestaurante.setEnabled(true);
        Passgenerator ps = new Passgenerator();
        String generada = aleatorio();
        String password = ps.getPassword(generada);
        newuserrestaurante.setPassword(password);

        List<Authority> lista = authorityRepository.findAll();
        ArrayList<Authority> list = new ArrayList<Authority>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getAuthority().equals("ROL_RESTAURANTE")){
                list.add(lista.get(i));
                // System.out.println("Respuesta => " + lista.get(i).getAuthority());
            }
        }
        newuserrestaurante.setAuthority(list);
        // System.out.println("List => " + list);
        String body = "Estimado usuario, \n  Gracias por contactarnos al correo \n " +
        "Los datos de acceso son: \n user: El correo que usó en el registro.\n " + 
        "contraseña: " + generada;
        emailService.sendEmail(r.getCorreo(), body, "¡¡¡¡GRACIAS!!!!");
        serviceuser.save(newuserrestaurante);
		return "redirect:/";
    }
    
    private String aleatorio(){
        char [] chars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        int charsLength = chars.length;
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i=0;i<6;i++){
            buffer.append(chars[random.nextInt(charsLength)]);
        }
        // System.out.println("Random String " + buffer.toString());
        return buffer.toString();
    }
}
