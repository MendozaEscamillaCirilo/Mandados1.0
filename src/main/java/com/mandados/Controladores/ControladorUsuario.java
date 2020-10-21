package com.mandados.Controladores;

import com.mandados.Entidades.Repartidor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ControladorUsuario {
    // @Autowired
    // private IRestauranteService servicerestaurante;

    @GetMapping("/resrep")
    public String registroRepartidor(Model model){
    	// model.addAttribute("userrepartidor", new Repartidor());
        return "/registro/repartidor";
    }

    @PostMapping("/register_repartidor")
	public String save(@Validated Repartidor r, Model model){
        // servicerestaurante.save(r);
        // User newuserrestaurante = new User();
        // newuserrestaurante.setUsername(r.getCorreo());
        // newuserrestaurante.setEnabled(true);
        // Passgenerator ps = new Passgenerator();
        // String generada = aleatorio();
        // String password = ps.getPassword(generada);
        // newuserrestaurante.setPassword(password);

        // List<Authority> lista = authorityRepository.findAll();
        // ArrayList<Authority> list = new ArrayList<Authority>();
        // for(int i=0;i<lista.size();i++){
        //     if(lista.get(i).getAuthority().equals("ROL_RESTAURANTE")){
        //         list.add(lista.get(i));
        //         // System.out.println("Respuesta => " + lista.get(i).getAuthority());
        //     }
        // }
        // newuserrestaurante.setAuthority(list);
        // // System.out.println("List => " + list);
        // String body = "Estimado usuario, \n  Gracias por contactarnos al correo \n " +
        // "Los datos de acceso son: \n user: El correo que usó en el registro.\n " + 
        // "contraseña: " + generada;
        // emailService.sendEmail(r.getCorreo(), body, "¡¡¡¡GRACIAS!!!!");
        // serviceuser.save(newuserrestaurante);
		return "redirect:/";
    }
}
