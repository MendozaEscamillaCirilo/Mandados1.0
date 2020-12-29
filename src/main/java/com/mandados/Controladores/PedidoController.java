package com.mandados.Controladores;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.DestinosEntity;
import com.mandados.Entidades.PedidosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.DestinosRepository;
import com.mandados.Repository.PedidoRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PedidoController {
    @Autowired
    private EntityManager em;
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private DestinosRepository destinosrepository;
    @Autowired
    private PedidoRepository pedidorepository;
    @Autowired
    private ProductoRepository productorepository;
    @Autowired
    private UserRepository userrepository;
    @GetMapping("/listapedido")
    public String listarorden(Authentication auth, Model model) {
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_REPARTIDOR")){
            model.addAttribute("pedidos", pedidorepository.findAll());
            model.addAttribute("activo", true);
        }
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            model.addAttribute("activo", metodosextra.getComercioLogueado(auth).getEstatus());
            UserDetails ud = (UserDetails)auth.getPrincipal();
            ComerciosEntity comercio = comerciorepository.findByEmail(ud.getUsername());
            Query pendientes = em.createNativeQuery("SELECT pe.id,fecha,horaentrega,horapedido,horarecoleccion,total,destino_id,repartidor_id,user_id FROM pedidos as pe INNER JOIN pedidos_productos as pp on pp.pedido_id = pe.id INNER JOIN productos as pr on pr.id = pp.producto_id INNER JOIN comercios as c on c.id = pr.comercio_id where c.nombre like '%" + comercio.getNombre() + "%'");
            // Query entregados = em.createNativeQuery("SELECT horaentrega FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio.getNombre() + "%' and horaentrega is not NULL");
            // Query todos = em.createNativeQuery("SELECT fecha,horaentrega,horapedido,horarecoleccion,total FROM comercios as c inner join productos as p on p.comercio_id = c.id inner join pedidos_productos as pp on pp.producto_id = p.id inner join pedidos as pe on pe.id = pp.pedido_id where c.nombre like '%" + comercio.getNombre() + "%'");
            // TypedQuery<PedidosEntity> todos = em.createQuery("SELECT pe.id,fecha,horaentrega,horapedido,horarecoleccion,total,destino_id,repartidor_id,user_id FROM pedidos as pe INNER JOIN pedidos_productos as pp on pp.pedido_id = pe.id INNER JOIN productos as pr on pr.id = pp.producto_id INNER JOIN comercios as c on c.id = pr.comercio_id where c.nombre like '%" + comercio.getNombre() + "%'",PedidosEntity.class);
            System.out.println("//////////////////////////////////////////////");
            List lista = pendientes.getResultList();
            System.out.println("//////////////////////////////////////////////");
            Object ped = lista.get(0);
            System.out.println(ped.getClass().getName());
            System.out.println(ped.getClass().getFields().clone());
            // TypedQuery<PedidosEntity> todos = em.createQuery("SELECT * FROM Pedidos p",PedidosEntity.class);
            // List<PedidosEntity> lista = todos.getResultList();
            // System.out.println(lista);
            // System.out.println(lista.get(0));
            System.out.println("//////////////////////////////////////////////");
            // System.out.println(lista.get(0).getClass());
            // model.addAttribute("pedidos", lista);
            model.addAttribute("pedidos", pedidorepository.findAll());
            // model.addAttribute("pedidos", pedidorepository.searchByNombreLike(comercio.getNombre()));
            // pedidorepository.searchByFecha("2020");
            // System.out.println(comercio.getNombre());
            // pedidorepository.searchByNombreLike(comercio.getNombre());
        }else{
            model.addAttribute("pedidos", pedidorepository.findAll());
            model.addAttribute("activo", true);
        }
        metodosextra.obtUsuario(model,auth);
        return "listar/pedido";
    }

    @GetMapping("/registrarpedido")
    public String registrarPedido(Authentication auth, Model model, 
                                        @RequestParam("nombre")String nombre,
                                        @RequestParam("primerapellido")String primerapellido,
                                        @RequestParam("segundoapellido")String segundoapellido,
                                        @RequestParam("calle")String calle,
                                        @RequestParam("numero")String numero,
                                        @RequestParam("colonia")String colonia,
                                        @RequestParam("municipio")String municipio,
                                        @RequestParam("telefono")String telefono,
                                        @RequestParam("ids")String ids,
                                        @RequestParam("valores")String valores
                                        ){
        DestinosEntity destino = new DestinosEntity(nombre, primerapellido, segundoapellido, calle, Integer.parseInt(numero), colonia, municipio, telefono);
        destinosrepository.save(destino);
        PedidosEntity pedido = new PedidosEntity(new java.sql.Date(Calendar.getInstance().getTimeInMillis()), 
                                                 new java.sql.Time(Calendar.getInstance().getTimeInMillis()),
                                                 destino, 
                                                 userrepository.findByUsername(auth.getName()).get());
        Double total = 0.0;
        Set<ProductosEntity> listadeproductos = new HashSet<ProductosEntity>();
        String [] productosexistentes = ids.split(",");
        String [] productosexistentesvalores = valores.split(",");
        for (int i = 1; i < productosexistentes.length; i++) {
            ProductosEntity producto = productorepository.findById(Long.parseLong(productosexistentes[i])).get();
            listadeproductos.add(producto);
            total+=producto.getPrecio()*Integer.parseInt(productosexistentesvalores[i]);
        }
        pedido.setTotal(total);
        pedido.setProductos(listadeproductos);
        pedidorepository.save(pedido);
        return listarorden(auth, model);
    }
}
