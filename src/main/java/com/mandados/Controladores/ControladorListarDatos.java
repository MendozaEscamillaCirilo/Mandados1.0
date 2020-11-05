package com.mandados.Controladores;

import java.util.ArrayList;
import java.util.List;

// import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.PedidoRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.RepartidorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ControladorListarDatos {
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private RepartidorRepository repartidorRepository;
    @Autowired
    private CategoriaRepository categoriarepository;
    @Autowired
    private PedidoRepository pedidorepository;
    @Autowired
    private ProductoRepository productorepository;
    
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
    @GetMapping("/listarepartidor")
    public String listarrepartidor(Model model) {
        model.addAttribute("repartidores", repartidorRepository.findAll());
        model.addAttribute("repartidor", new RepartidoresEntity());
        return "listar/repartidor";	    
    }
    @GetMapping("/listaorden")
    public String listarorden(Model model) {
        model.addAttribute("pedidos", pedidorepository.findAll());
        return "listar/orden";	    
    }
    @GetMapping("/listaproducto")
    public String listarproducto(Model model) {
        model.addAttribute("productos", productorepository.findAll());
        model.addAttribute("producto", new ProductosEntity());
        model.addAttribute("categorias", categoriarepository.findAll());
        return "listar/producto";	    
    }
    @GetMapping("/listacatalogo")
    public String listarcatalogo(Model model) {
        // model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/carta";	    
    }
}
