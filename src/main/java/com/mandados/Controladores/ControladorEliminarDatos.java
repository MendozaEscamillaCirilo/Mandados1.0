package com.mandados.Controladores;

import java.util.Optional;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.Servicios.Categoria.ICategoriaService;
import com.mandados.Servicios.Comercio.IComercioService;
import com.mandados.Servicios.Pedido.IPedidoService;
import com.mandados.Servicios.Producto.IProductoService;
import com.mandados.Servicios.Repartidor.IRepartidorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ControladorEliminarDatos {
    @Autowired
    public ComercioRepository comerciorepository;
    @Autowired
    public RepartidorRepository repartidorrepository;
    @Autowired
    public UserRepository userrepository;
    @Autowired
    public ICategoriaService categoriaservice;
    @Autowired
    public IComercioService comercioservice;
    @Autowired
    public IPedidoService pedidoservice;
    @Autowired
    public IProductoService productoservice;
    @Autowired
    public IRepartidorService Repartidorservice;
    @GetMapping("/eliminar/{id}")
	public String eliminarComercio(@PathVariable Long id, Model model) {
        ComerciosEntity comercio = comerciorepository.findById(id).get();
        comercio.setEstatus(false);
		comercioservice.save(comercio);
		return "redirect:/listacomercio";
	}
    @GetMapping("/eliminarcategoria/{id}")
	public String eliminarCategoria(@PathVariable int id, Model model) {
        categoriaservice.delete(id);
		return "redirect:listar/categoria";
	}
    @GetMapping("/eliminarorden/{id}")
	public String eliminarPedido(@PathVariable int id, Model model) {
        pedidoservice.delete(id);
		return "redirect:listar/orden";
	}
    @GetMapping("/eliminarproducto/{id}")
	public String eliminarProducto(@PathVariable int id, Model model) {
        productoservice.delete(id);
		return "redirect:listar/producto";
	}
    @GetMapping("/eliminarrepartidor/{id}")
	public String eliminarRepartidor(@PathVariable Long id, Model model) {
        System.out.println("Llegó hasta aquí");
        try {
            Repartidorservice.delete(id);
            model.addAttribute("eliminado", true);
            obtUsuario(model);
            model.addAttribute("repartidores", repartidorrepository.findAll());
            model.addAttribute("repartidor", new RepartidoresEntity());
            model.addAttribute("noerror", true);
        } catch (Exception e) {
            model.addAttribute("noeliminado", true);
            System.out.println("Sucedió un error");
            System.out.println(e);
        }
		return "redirect:/listarepartidor";
    }
    
    public void obtUsuario(Model model){
        Optional<User>lista = userrepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        model.addAttribute("usuario", user1);
        model.addAttribute("foto", "logos/"+user1.getUsername() + ".jpg");
    }
}
