package com.mandados.Controladores;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.PedidosEntity;
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
    private EntityManager em;
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
    @GetMapping("/listatipocomercio")
    public String listatipocomercio(Model model){
        metodosextra.obtUsuario(model);
        model.addAttribute("activo", true);
        return "listar/tipocomercio";
    }
    @GetMapping("/listacomercio")
    public String listarcomercio(Model model) {
        metodosextra.obtUsuario(model);
        model.addAttribute("activo", true);
        model.addAttribute("comercios", comerciorepository.findAll());
        model.addAttribute("sucursales", sucursalrepository.findAll());
        model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/comercio";	    
    }
    @GetMapping("/listarestaurante")
    public String listarcomercior(Model model) {
        metodosextra.obtUsuario(model);
        model.addAttribute("activo", true);
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
        model.addAttribute("activo", true);
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
        model.addAttribute("activo", true);
        model.addAttribute("repartidores", repartidorRepository.findAll());
        model.addAttribute("repartidor", new RepartidoresEntity());
        return "listar/repartidor";	    
    }
	@GetMapping("/listarcategoria")
    public String listarcategoria(Model model, Authentication auth) {
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            model.addAttribute("activo", metodosextra.getComercioLogueado(auth).getEstatus());
        }else{
            model.addAttribute("activo", true);
        }
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
    public String listarorden(Authentication authentication, Model model) {
        if((authentication.getAuthorities().toArray()[0]+"").equals("ROL_REPARTIDOR")){
            model.addAttribute("pedidos", new ArrayList<PedidosEntity>());
            model.addAttribute("activo", true);
        }
        if((authentication.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            model.addAttribute("activo", metodosextra.getComercioLogueado(authentication).getEstatus());
            // String comercio = metodosextra.getComercioLogueado(authentication).getNombre();
            model.addAttribute("establecerhorario", true);
            // Query pedidos = em.createNativeQuery("SELECT pe.fecha, hora_pedido, hora_recoleeccion, hora_entrega, total FROM pedidos AS pe INNER JOIN pedidos_productos AS pp ON pp.pedido_id = pe.id INNER JOIN productos AS p ON pp.producto_id = p.id INNER JOIN comercios AS c ON c.id = p.comercio_id where c.nombre like '%" + comercio + "%'");
            // List<Object[]> results = pedidos.getResultList();
            // List<PedidosEntity> result =  results
            //                                 .stream()
            //                                 .map(result -> new PedidosEntity((String) result[0]))
            //                                 .collect(Collectors.toList());
            // List<String> lista = pedidos.getResultList();
            model.addAttribute("pedidos", pedidorepository.findAll());
        }else{
            model.addAttribute("pedidos", pedidorepository.findAll());
            model.addAttribute("activo", true);
        }
        metodosextra.obtUsuario(model);
        return "listar/orden";	    
    }
    @GetMapping("/listaproducto")
    public String listarproducto(Model model, Authentication auth) {
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            model.addAttribute("activo", metodosextra.getComercioLogueado(auth).getEstatus());
        }else{
            model.addAttribute("activo", true);
        }
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
    public String listarcatalogo(Model model,Authentication auth) {
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            model.addAttribute("activo", metodosextra.getComercioLogueado(auth).getEstatus());
        }else{
            model.addAttribute("activo", true);
        }
        metodosextra.obtUsuario(model);
        // model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/carta";	    
    }
    @GetMapping("/callcenter")
    public String listarcallcenter(Model model) {
        model.addAttribute("usuarios", userrepository.findByUsername("ROL_CALLCENTER"));
        model.addAttribute("roles", authorityrepository.findAll());
        model.addAttribute("usuario", new User());
        model.addAttribute("activo", true);
        metodosextra.obtUsuario(model);
        return "listar/callcenter";	    
    }
    @GetMapping("/home")
    public String userPage(Authentication authentication, Model model) {
        if((authentication.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            UserDetails ud = (UserDetails)authentication.getPrincipal();
            String comercio = comerciorepository.findByEmail(ud.getUsername()).getNombre();
            model.addAttribute("establecerhorario", true);
            Query pendientes = em.createNativeQuery("SELECT hora_entrega FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio + "%' and hora_entrega is NULL");
            Query entregados = em.createNativeQuery("SELECT hora_entrega FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio + "%' and hora_entrega is not NULL");
            model.addAttribute("activo", metodosextra.getComercioLogueado(authentication).getEstatus());
            System.out.println(metodosextra.getComercioLogueado(authentication).getEstatus());
            model.addAttribute("ordenespendientes", pendientes.getResultList().size());
            model.addAttribute("ordenescompletadas", entregados.getResultList().size());
            model.addAttribute("apertura", comerciorepository.findByNombre(comercio).getHoraApertura());
            model.addAttribute("cierre", comerciorepository.findByNombre(comercio).getHoraCierre());
        }
        // System.out.println("/////////////////////////RESULTADO//////////////////////////////////////");
        // System.out.println(authentication.getAuthorities().toArray());
        if((authentication.getAuthorities().toArray()[0]+"").equals("ROL_ADMIN")||(authentication.getAuthorities().toArray()[0]+"").equals("ROL_CALLCENTER")){
            model.addAttribute("totalcomercios", comerciorepository.count());
            model.addAttribute("totalrepartidores", repartidorrepository.count());
            model.addAttribute("ordenescompletadas", metodosextra.getDatesOnListNoExist(pedidorepository.findAll()).size());
            model.addAttribute("ordenespendientes", metodosextra.getDatesOnListExist(pedidorepository.findAll()).size());
            model.addAttribute("activo", true);
        }
        if((authentication.getAuthorities().toArray()[0]+"").equals("ROL_REPARTIDOR")){
            model.addAttribute("activo", true);
        }
        metodosextra.obtUsuario(model);
        return "home";	    
    }
}
