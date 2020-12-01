package com.mandados.Controladores;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Servicios.Comercio.ComercioService;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class ControladorEditarDatos {
    @Autowired
    private ComercioService comercioservice;
    @Autowired
    private ControladorListarDatos clistar;
    @Autowired
    private MetodosExtra metodosextra;
    @GetMapping("/configurar")
    public String configurarHoraDeComerios(Authentication authentication, Model model, @RequestParam("apertura") java.sql.Time apertura, @RequestParam("cierre") java.sql.Time cierre){
        ComerciosEntity comercio = metodosextra.getComercioLogueado(authentication);
        comercio.setHoraApertura(apertura);
        comercio.setHoraCierre(cierre);
        comercioservice.save(comercio);
        return clistar.userPage(authentication, model);
    }
}
