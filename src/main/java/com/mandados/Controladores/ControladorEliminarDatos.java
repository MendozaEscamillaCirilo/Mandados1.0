package com.mandados.Controladores;

import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.Servicios.Categoria.ICategoriaService;
import com.mandados.Servicios.Comercio.IComercioService;
import com.mandados.Servicios.Pedido.IPedidoService;
import com.mandados.Servicios.Producto.IProductoService;
import com.mandados.Servicios.Repartidor.IRepartidorService;
import com.mandados.Servicios.User.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ControladorEliminarDatos {
    @Autowired
    public CategoriaRepository categoriarepository;
    @Autowired
    public ComercioRepository comerciorepository;
    @Autowired
    public ProductoRepository productorepository;
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
    public IRepartidorService repartidorservice;
    @Autowired
    public IUserService userservice;
    @GetMapping("/eliminar/{id}")
	public String eliminarComercio(@PathVariable Long id, Model model) {
        ComerciosEntity comercio = comerciorepository.findById(id).get();
        comercio.setEstatus(false);
		comercioservice.save(comercio);
		return "redirect:/listacomercio";
	}
    @GetMapping("/eliminarcategoria/{id}")
	public String eliminarCategoria(@PathVariable Long id, Model model) {
        CategoriasEntity categoria = categoriarepository.findById(id).get();
        categoria.setEstatus(false);
        categoriaservice.save(categoria);
		return "redirect:listar/categoria";
	}
    @GetMapping("/eliminarorden/{id}")
	public String eliminarPedido(@PathVariable Long id, Model model) {
        pedidoservice.delete(id);
		return "redirect:listar/orden";
	}
    @GetMapping("/eliminarproducto/{id}")
	public String eliminarProducto(@PathVariable Long id, Model model) {
        ProductosEntity producto = productorepository.findById(id).get();
        producto.setEstatus(false);
        productoservice.save(producto);
		return "redirect:listar/producto";
    }
    @GetMapping("/eliminarusuario/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model) {
        userservice.delete(id);
		return "redirect:/usuarios";
	}
    @GetMapping("/eliminarrepartidor/{id}")
	public String eliminarRepartidor(@PathVariable Long id, Model model) {
        RepartidoresEntity repartidor = repartidorrepository.findById(id).get();
        repartidor.setEstatus(false);
        repartidorservice.save(repartidor);
		return "redirect:/listarepartidor";
    }
}
