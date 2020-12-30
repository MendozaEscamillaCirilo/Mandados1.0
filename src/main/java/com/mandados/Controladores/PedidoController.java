package com.mandados.Controladores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.DestinosEntity;
import com.mandados.Entidades.PedidosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.DestinosRepository;
import com.mandados.Repository.PedidoRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class PedidoController {
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
    private RepartidorRepository repartidorrepository;
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
            List<PedidosEntity> lista = pedidorepository.findAll();
            List<PedidosEntity> pedidosdelcomercio = new ArrayList<PedidosEntity>();
            Iterator<PedidosEntity> iterador = lista.iterator();
            while (iterador.hasNext()) {
                PedidosEntity pedido = iterador.next();
                if ((pedido.getProductos().iterator().next()).getComercio().getId()==comercio.getId()) {
                    pedidosdelcomercio.add(pedido);
                }
            }
            model.addAttribute("pedidos", pedidosdelcomercio);
        }else{
            model.addAttribute("pedidos", pedidorepository.findAll());
            model.addAttribute("activo", true);
            model.addAttribute("repartidores", repartidorrepository.findAll());
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
                                        @RequestParam("valores")String valores,
                                        @RequestParam("comentarios")String comentarios
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
        pedido.setComentarios(comentarios);
        pedidorepository.save(pedido);
        return listarorden(auth, model);
    }
    @GetMapping("/asignarrepartidor")
    public String postMethodName(@RequestParam("select") RepartidoresEntity repartidor,@RequestParam("idp")Long id,Model model, Authentication auth) {
        PedidosEntity pedido = pedidorepository.findById(id).get();
        pedido.setRepartidor(repartidor);
        pedidorepository.save(pedido);
        repartidor.setEstatus(false);
        repartidorrepository.save(repartidor);
        return listarorden(auth, model);
    }
    
}
