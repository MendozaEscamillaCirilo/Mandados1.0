package com.mandados.Controladores;

import com.mandados.Entidades.TiposcomerciosEntity;
import com.mandados.Repository.TipoComercioRepository;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TipocomercioController {
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private TipoComercioRepository tipocomerciorepository;
    @GetMapping("/listatipocomercio")
    public String listarTipoComercio(Model model, Authentication auth){
        metodosextra.obtUsuario(model, auth);
        model.addAttribute("activo", true);
        model.addAttribute("tiposcomercios", tipocomerciorepository.findAll());
        model.addAttribute("tipocomercio", new TiposcomerciosEntity());
        return "listar/tipocomercio";
    }
    @PostMapping("/registrotipocomercio")
    public String registrarTipocomercio(@Validated TiposcomerciosEntity tipocomercio, Model model, Authentication auth){
        try {
            tipocomerciorepository.save(tipocomercio);
        } catch (Exception e) {
            System.out.println("SUCEDIO UN ERROR");
        }
        return listarTipoComercio(model, auth);
    }
}
