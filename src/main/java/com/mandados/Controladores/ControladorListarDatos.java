package com.mandados.Controladores;

import java.util.ArrayList;
import java.util.List;

import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.PedidoRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.config.MetodosExtra;
import com.mandados.Repository.SucursalRepository;
import com.mandados.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ControladorListarDatos {
    @Autowired
    private AuthorityRepository authorityrepository;
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
    private RepartidorRepository repartidorrepository;
    @Autowired
    private SucursalRepository sucursalrepository;
    @Autowired
    private UserRepository userrepository;
    @Autowired
    private MetodosExtra metodosextra;
    @GetMapping("/listacomercio")
    public String listarcomercio(Model model) {
        metodosextra.obtUsuario(model);
        model.addAttribute("comercios", comerciorepository.findAll());
        model.addAttribute("sucursales", sucursalrepository.findAll());
        model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/comercio";	    
    }
    @GetMapping("/listarestaurante")
    public String listarcomercior(Model model) {
        metodosextra.obtUsuario(model);
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
        metodosextra.obtUsuario(model);
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
        metodosextra.obtUsuario(model);
        model.addAttribute("repartidores", repartidorRepository.findAll());
        model.addAttribute("repartidor", new RepartidoresEntity());
        return "listar/repartidor";	    
    }
	@GetMapping("/listarcategoria")
    public String listarcategoria(Model model, Authentication auth) {
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        metodosextra.obtUsuario(model);
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
        metodosextra.obtUsuario(model);
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
        metodosextra.obtUsuario(model);
        return "listar/producto";	    
    }
    @GetMapping("/listacatalogo")
    public String listarcatalogo(Model model) {
        metodosextra.obtUsuario(model);
        // model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/carta";	    
    }
    @GetMapping("/callcenter")
    public String listarcallcenter(Model model) {
        model.addAttribute("usuarios", userrepository.findByUsername("ROL_CALLCENTER"));
        model.addAttribute("roles", authorityrepository.findAll());
        model.addAttribute("usuario", new User());
        metodosextra.obtUsuario(model);
        return "listar/callcenter";	    
    }
    @GetMapping("/home")
    public String userPage(Authentication authentication, Model model) {
        model.addAttribute("totalcomercios", comerciorepository.count());
        model.addAttribute("totalrepartidores", repartidorrepository.count());
        model.addAttribute("ordenescompletadas", metodosextra.getDatesOnListNoExist(pedidorepository.findAll()).size());
        model.addAttribute("ordenespendientes", metodosextra.getDatesOnListExist(pedidorepository.findAll()).size());
        metodosextra.obtUsuario(model);
        return "home";	    
    }
}
