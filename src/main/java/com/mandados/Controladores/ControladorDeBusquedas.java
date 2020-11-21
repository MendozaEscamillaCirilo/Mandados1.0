package com.mandados.Controladores;

import java.util.List;

import com.mandados.Entidades.ProductosEntity;
import com.mandados.Repository.ProductoRepository;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class ControladorDeBusquedas {
    @Autowired
    private ProductoRepository productorepository;
    @Autowired
    private MetodosExtra metodosextra;
    @GetMapping("/getproductosabc")
    public String paraPruebaDeHome(@RequestParam("search") String producto, Model model){
        model.addAttribute("tablavisible", true);
        model.addAttribute("productos", productorepository.findByNombreContaining(producto));
        metodosextra.obtUsuario(model);
        return "home";
    }
}
