package com.mandados.Controladores;

import java.util.ArrayList;
import java.util.List;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Repository.ComercioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ControladorComercio {
    @Autowired
    private ComercioRepository comerciorepository;
    @GetMapping("/listacomercio")
    public String listarcomercio(Model model) {
        model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/comercio";	    
    }
    @GetMapping("/listarestaurante")
    public String listarcomercior(Model model) {
        List<ComerciosEntity> lista = comerciorepository.findAll();
        List<ComerciosEntity> lista1 = new ArrayList<ComerciosEntity>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getTipoComercio().getNombre().equals("RESTAURANTE")){
                lista1.add(lista.get(i));
            }
        }
        model.addAttribute("comercios", lista1);
        return "listar/restaurante";	    
    }
    @GetMapping("/listatienda")
    public String listarcomerciot(Model model) {
        List<ComerciosEntity> lista = comerciorepository.findAll();
        List<ComerciosEntity> lista1 = new ArrayList<ComerciosEntity>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getTipoComercio().getNombre().equals("TIENDA")){
                lista1.add(lista.get(i));
            }
        }
        model.addAttribute("comercios", lista1);
        return "listar/tienda";	    
    }
}
