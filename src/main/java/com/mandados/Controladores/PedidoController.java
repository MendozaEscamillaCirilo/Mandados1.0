package com.mandados.Controladores;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.PedidoRepository;
// import com.like.mandados.Repositories.ProductoRepository;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidoController {
    @Autowired
    private EntityManager em;
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private PedidoRepository pedidorepository;
    // @Autowired
    // private ProductoRepository productorepository;
    @GetMapping("/listapedido")
    public String listarorden(Authentication auth, Model model) {
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_REPARTIDOR")){
            model.addAttribute("pedidos", pedidorepository.findAll());
            model.addAttribute("activo", true);
        }
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            model.addAttribute("activo", metodosextra.getComercioLogueado(auth).getEstatus());
            model.addAttribute("pedidos", pedidorepository.findAll());
            UserDetails ud = (UserDetails)auth.getPrincipal();
            ComerciosEntity comercio = comerciorepository.findByEmail(ud.getUsername());
            // Query pendientes = em.createNativeQuery("SELECT horaentrega FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio.getNombre() + "%' and horaentrega is NULL");
            // Query entregados = em.createNativeQuery("SELECT horaentrega FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio.getNombre() + "%' and horaentrega is not NULL");
            Query todos = em.createNativeQuery("SELECT horaentrega FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio.getNombre() + "%'");
            List lista = todos.getResultList();
            System.out.println(lista);
        }else{
            model.addAttribute("pedidos", pedidorepository.findAll());
            model.addAttribute("activo", true);
        }
        metodosextra.obtUsuario(model,auth);
        return "listar/pedido";	    
    }
}
