package com.mandados.Controladores;

import com.mandados.Entidades.User;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.config.MetodosExtra;
import com.mandados.config.Passgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private UserRepository userrepository;
    @Autowired
    private AuthorityRepository authorityrepository;
    @GetMapping("/usuarios")
    public String listarusuario(Model model,Authentication auth) {
        model.addAttribute("usuarios", userrepository.findAll());
        model.addAttribute("roles", authorityrepository.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("activo", true);
        metodosextra.obtUsuario(model,auth);
        return "listar/usuario";	    
    }
    @PostMapping(value="/registrousuario")
    public String registrarUsuarios(Model model, Authentication auth, 
                                    @RequestParam("password") String generada,
                                    @Validated User user) {
        Passgenerator ps = new Passgenerator();
        String password = ps.getPassword(generada);
        user.setPassword(password);
        user.setEnabled(true);
        user.setAuthority(authorityrepository.findByAuthority("ROL_CALLCENTER"));
        userrepository.save(user);
        return listarusuario(model, auth);
    }
    @GetMapping("/eliminarusuario/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model, Authentication auth) {
        User user = userrepository.findById(id).get();
        user.setEnabled(user.isEnabled()==false ? true : false);
        userrepository.save(user);
		return listarusuario(model, auth);
    }
    @GetMapping(value="/editarusuario")
    public String editarusuario(Model model, Authentication auth) {
        User user = userrepository.findByUsername(auth.getName()).get();
        model.addAttribute("usuario", user);
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            model.addAttribute("activo", metodosextra.getComercioLogueado(auth).getEstatus());
        }else{
            model.addAttribute("activo", true);
        }
        metodosextra.obtUsuario(model,auth);
        return "editarusuario";	    
    }
    @PostMapping("/reset")
    public String contraseniaolvidada(@RequestParam("username") String username, Model model){
        try {
            User usuario = userrepository.findByUsername(username).get();
            Passgenerator ps = new Passgenerator();
            String generada = metodosextra.aleatorio();
            String password = ps.getPassword(generada);
            usuario.setPassword(password);
            try {
                userrepository.save(usuario);
            } catch (Exception e) {
                System.out.println("ERROR AL GUARDAR");
                return "registro/exitoso";
            }
            try {
                metodosextra.sendEmailRemember(usuario.getUsername(), generada);
                model.addAttribute("reset",true);
                return "registro/exitoso";
            } catch (Exception e) {
                System.out.println("ERROR AL ENVIAR EMAIL");
                System.out.println(e);
            }
        } catch (Exception e) {
            model.addAttribute("existe", true);
            return "resetcontrasenia";
        }
        return "resetcontrasenia";
    }
    
    @GetMapping("/probaralgo")
    public String solamentedeprueba(Model model) {
        model.addAttribute("totalproductos",3);
        return "paraprueba";	    
    }
}
