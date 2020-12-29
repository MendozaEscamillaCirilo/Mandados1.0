package com.mandados.Controladores;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.DestinosEntity;
import com.mandados.Entidades.ProductosParaPedidos;
import com.mandados.Entidades.User;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.PedidoRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.config.MetodosExtra;
import com.mandados.config.Passgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping
public class ControladorListarDatos {
    @Autowired
    private EntityManager em;
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private PedidoRepository pedidorepository;
    @Autowired
    private ProductoRepository productorepository;
    @Autowired
    private RepartidorRepository repartidorrepository;
    @Autowired
    private UserRepository userrepository;
    @Autowired
    private MetodosExtra metodosextra;
    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if((authentication.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            UserDetails ud = (UserDetails)authentication.getPrincipal();
            String comercio = comerciorepository.findByEmail(ud.getUsername()).getNombre();
            model.addAttribute("establecerhorario", true);
            model.addAttribute("nombrecomercio", comercio);
            Query pendientes = em.createNativeQuery("SELECT horaentrega FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio + "%' and horaentrega is NULL");
            Query entregados = em.createNativeQuery("SELECT horaentrega FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio + "%' and horaentrega is not NULL");
            model.addAttribute("activo", metodosextra.getComercioLogueado(authentication).getEstatus());
            System.out.println(metodosextra.getComercioLogueado(authentication).getEstatus());
            model.addAttribute("ordenespendientes", pendientes.getResultList().size());
            model.addAttribute("ordenescompletadas", entregados.getResultList().size());
            model.addAttribute("apertura", comerciorepository.findByNombre(comercio).getHoraapertura());
            model.addAttribute("cierre", comerciorepository.findByNombre(comercio).getHoracierre());
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
        metodosextra.obtUsuario(model,authentication);
        return "home";	    
    }
    
    @PostMapping("/editarfoto")
    public String editarfoto(@RequestParam("file") MultipartFile imagen,Model model,Authentication authentication){
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static//logos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            Optional<User>lista = userrepository.findByUsername(authentication.getName());
            User user1 = lista.get();
            try {
                byte[] bytesImgenes = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + user1.getUsername()+".jpg");
                Files.write(rutaCompleta,bytesImgenes);
                user1.setImagen(user1.getUsername().split("@")[0]);
                userrepository.save(user1);
            } catch (Exception e) {System.out.println(e);}
        }
        return home(authentication, model);
    }
    @PostMapping("/editarcontrasenia")
    public String guardarDatos(@Validated User user, Model model,Authentication authentication){
        Optional<User>lista = userrepository.findByUsername(authentication.getName());
        User user1 = lista.get();
        Passgenerator ps = new Passgenerator();
        user1.setPassword(ps.getPassword(user.getPassword()));
        userrepository.save(user1);
        metodosextra.sendEmail(user1.getUsername());
        metodosextra.obtUsuario(model,authentication);
        return home(authentication, model);
    }
    @GetMapping("/generarqr")
    public String generarNuevoQR(Model model, Authentication auth){
        metodosextra.generarQR(metodosextra.getComercioLogueado(auth).getNombre());
        return home(auth, model);
    }
    @GetMapping("/configurar")
    public String configurarHoraDeComerios(Authentication authentication, Model model, @RequestParam("apertura") java.sql.Time apertura, @RequestParam("cierre") java.sql.Time cierre){
        ComerciosEntity comercio = metodosextra.getComercioLogueado(authentication);
        comercio.setHoraapertura(apertura);
        comercio.setHoraCierre(cierre);
        comerciorepository.save(comercio);
        return home(authentication, model);
    }
    @RequestMapping("cargartablaproductosdepedido/{id}")
    public String requestMethodName(Model model,@PathVariable("id") Long id,
                                                @RequestParam("cantidad")int cantidad,
                                                @RequestParam("lista")String lista,
                                                @RequestParam("cantidades")String cantidades,
                                                @RequestParam("comentario")String comentario,
                                                @RequestParam("comentarios")String comentarios
                                                ) {
        model.addAttribute("divtabladepedido", true);
        List<ProductosParaPedidos> productos = new ArrayList<ProductosParaPedidos>();
        productos.add(metodosextra.convertirEnProductosParaPedido(productorepository.findById(id).get(), cantidad,comentario));
        if(!lista.equals("1000")){
            String [] productosexistentes = lista.split(",");
            String [] productosexistentesvalores = cantidades.split(",");
            String [] comentarioss = comentarios.split(",");
            for (int i = 1; i < productosexistentes.length; i++) {
                productos.add(metodosextra.convertirEnProductosParaPedido(productorepository.findById(Long.parseLong(productosexistentes[i])).get(), Integer.parseInt(productosexistentesvalores[i]),comentarioss[i]));
            }
        }
        double total = 0.0;
        for (int i = 0; i < productos.size(); i++) {
            total+=productos.get(i).getTotal();
        }
        model.addAttribute("productosagregados", productos);
        model.addAttribute("totalp", total);
        model.addAttribute("destino",new DestinosEntity());
        return "includes/tabla";
    }
}
