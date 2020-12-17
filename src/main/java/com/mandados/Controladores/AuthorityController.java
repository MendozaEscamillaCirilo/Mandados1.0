package com.mandados.Controladores;

import com.mandados.Entidades.Authority;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class AuthorityController {
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private AuthorityRepository authorityrepository;
    @GetMapping(value="/roles")
    public String listarAuthority(Model model, Authentication auth) {
        model.addAttribute("authorities", authorityrepository.findAll());
        model.addAttribute("activo", true);
        metodosextra.obtUsuario(model, auth);
        return "listar/authority";
    }
    @PostMapping(value="/registroauthority")
    public String registrarAuthority(Model model, Authentication auth,
                                     @RequestParam("nombre") String nombre, 
                                     @RequestParam("descripcion") String descripcion) {
        Authority authority = new Authority();
        authority.setAuthority(nombre);
        authorityrepository.save(authority);
        return listarAuthority(model, auth);
    }
    @PostMapping(value="/editarauthority{id}")
    public String editarAuthority(Model model, Authentication auth,
                                  @RequestParam("id") Long id,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("descripcion") String descripcion) {
        Authority authority = authorityrepository.findById(id).get();
        authorityrepository.save(authority);
        return listarAuthority(model, auth);
    }
    
}
