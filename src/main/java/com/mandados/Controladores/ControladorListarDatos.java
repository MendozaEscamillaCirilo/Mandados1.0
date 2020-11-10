package com.mandados.Controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.PedidoRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/listacomercio")
    public String listarcomercio(Model model) {
        obtUsuario(model);
        model.addAttribute("comercios", comerciorepository.findAll());
        // model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/comercio";	    
    }
    @GetMapping("/listarestaurante")
    public String listarcomercior(Model model) {
        obtUsuario(model);
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
        obtUsuario(model);
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
        obtUsuario(model);
        model.addAttribute("repartidores", repartidorRepository.findAll());
        model.addAttribute("repartidor", new RepartidoresEntity());
        return "listar/repartidor";	    
    }
	@GetMapping("/listarcategoria")
    public String listarcategoria(Model model, Authentication auth) {
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        obtUsuario(model);
        model.addAttribute("categoriastotales", categoriarepository.findAll());
        if(userDetail.getUsername().equals("admin")){
            model.addAttribute("categoriasseleccionadas", categoriarepository.findAll());
        }else{
            model.addAttribute("categoriasseleccionadas", comerciosEntity.getCategorias());
        }
        model.addAttribute("categoria", new CategoriasEntity());
        return "listar/categoria";
    }
    @GetMapping("/listaorden")
    public String listarorden(Model model) {
        obtUsuario(model);
        model.addAttribute("pedidos", pedidorepository.findAll());
        return "listar/orden";	    
    }
    @GetMapping("/listaproducto")
    public String listarproducto(Model model, Authentication auth) {
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        if(userDetail.getUsername().equals("admin")){
            model.addAttribute("productos", productorepository.findAll());
            model.addAttribute("categorias", categoriarepository.findAll());
        }else{
            model.addAttribute("productos", productorepository.findByComercio(comerciosEntity));
            model.addAttribute("categorias", comerciosEntity.getCategorias());
        }
        model.addAttribute("producto", new ProductosEntity());
        obtUsuario(model);
        return "listar/producto";	    
    }
    @GetMapping("/listacatalogo")
    public String listarcatalogo(Model model) {
        obtUsuario(model);
        // model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/carta";	    
    }

    public void obtUsuario(Model model){
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        model.addAttribute("usuario", user1);
        model.addAttribute("foto", "logos/"+user1.getUsername() + ".jpg");
    }
}
