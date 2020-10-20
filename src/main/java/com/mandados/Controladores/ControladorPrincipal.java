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

// import com.mandados.Entidades.Authority;
import com.mandados.Entidades.Restaurante;
import com.mandados.Entidades.User;
import com.mandados.Restaurante.IRestauranteService;
import com.mandados.User.IUserService;
// import com.mandados.config.AuthorityRepository;
import com.mandados.config.Passgenerator;
import com.mandados.config.UserRepository;

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
    UserRepository userRepository;
    
    @GetMapping("/resreg")
    public String principal(Model model){
    	model.addAttribute("userrestaurante", new Restaurante());
        return "/registro/restaurante";
    }

    @PostMapping("/register_restaurante")
	public String save(@Validated Restaurante r, Model model){
        servicerestaurante.save(r);
        User newuserrestaurante = new User();
        newuserrestaurante.setUsername(r.getCorreo());
        newuserrestaurante.setEnabled(true);
        Passgenerator ps = new Passgenerator();
        String password = ps.getPassword("password");
        newuserrestaurante.setPassword(password);
        // List<Authority> nuevaLista = 
        // AuthorityRepository ar = 

        // List<GrantedAuthority> authorities = newuserrestaurante.getAuthority()
        // .stream()
        // .map(Authority -> new SimpleGrantedAuthority(Authority.getAuthority()))
        // .collect(Collectors.toList());
        // System.out.println(authorities);
        // newuserrestaurante.setAuthority(authorities);

        // Authority adminRole = userRepository.findByName("ROLE_ADMIN");
        // Set<Authority> lista = AuthorityRepository.findByUsername("ROLE_ADMIN");
        
        // newuserrestaurante.setAuthority();
        String body = "Estimado usuario, \n  Gracias por contactarnos al correo \n " +
        "Los datos de acceso son: \n user: El correo que usó en el registro.\n " + 
        "contraseña: password";
        emailService.sendEmail(r.getCorreo(), body, "¡¡¡¡GRACIAS!!!!");
        serviceuser.save(newuserrestaurante);
		return "login";
	}
    
    @Autowired
	private EmailService emailService;

	@GetMapping("/prueba-email")
	public String proveEmail(){
		return "formulario";
	}

	@RequestMapping(value = "/sendemail")
	public String sendEmail(String to, String body, String topic){
		emailService.sendEmail(to, body, topic);
		// String body = "Estimado usuario, \n  Gracias por contactarnos al correo";
		// emailService.sendEmail("cirmenes@gmail.com", body, "¡¡¡¡GRACIAS!!!!");
		// emailService.sendEmail("lilo2017c@gmail.com", body, "¡¡¡¡GRACIAS!!!!");
		// emailService.sendEmail("contato@cirilomendoza.com", body, "¡¡¡¡GRACIAS!!!!");
		return "redirect:/";
	}

	@RequestMapping(value = "/send")
	public String sendEmailMime(){
		// emailService.sendEmail(to, body, topic);
		String body = "Estimado usuario, \n  Gracias por contactarnos al correo";
		// emailService.sendEmail("cirmenes@gmail.com", body, "¡¡¡¡GRACIAS!!!!");
		emailService.sendEmail("lilo2017c@gmail.com", body, "¡¡¡¡GRACIAS!!!!");
		// emailService.sendEmail("contato@cirilomendoza.com", body, "¡¡¡¡GRACIAS!!!!");
		return "redirect:/";
	}
    
}
