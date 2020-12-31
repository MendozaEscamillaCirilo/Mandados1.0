package com.mandados.Controladores;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductoController {
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private CategoriaRepository categoriarepository;
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private ProductoRepository productorepository;
    @GetMapping(value="/listaproducto")
    public String listarProducto(Model model, Authentication auth) {

        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
            model.addAttribute("activo", metodosextra.getComercioLogueado(auth).getEstatus());
            model.addAttribute("productos", productorepository.findByComercio(comerciosEntity));
            model.addAttribute("categorias", comerciosEntity.getCategorias());
        }else{
            model.addAttribute("activo", true);
            model.addAttribute("productos", productorepository.findAll());
            model.addAttribute("categorias", categoriarepository.findAll());
        }
        model.addAttribute("producto", new ProductosEntity());
        metodosextra.obtUsuario(model, auth);
        return "listar/producto";
    }
    @PostMapping("/registroproducto")
    public String registroproducto(@Validated ProductosEntity producto,Model model,@RequestParam("file") MultipartFile imagen, Authentication auth){
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        producto.setComercio(comerciosEntity);
        producto.setImagen(metodosextra.crearImagenDelProducto(producto.getNombre(), imagen));
        producto.setEstatus(true);
        productorepository.save(producto);
        return listarProducto(model, auth);
    }
    @PostMapping("/editarproducto")
    public String editarproducto(@RequestParam("id") Long id,
                                 @RequestParam("nombre") String nombre, 
                                 @RequestParam("precio") Double precio,
                                 @RequestParam("contenido") String contenido,
                                 @RequestParam("descripcion") String descripcion,
                                 @RequestParam("file") MultipartFile imagen,
                                 Model model, Authentication auth){
        ProductosEntity producto = productorepository.findById(id).get();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setContenido(contenido);
        producto.setDescripcion(descripcion);
        if(!imagen.isEmpty()){ metodosextra.editarImagenDelProducto(producto.getImagen(), imagen); }
        productorepository.save(producto);
        return listarProducto(model,auth);
    }
    @GetMapping("/eliminarproducto/{id}")
    public String eliminarProducto(@PathVariable Long id, Model model, Authentication auth){
        ProductosEntity producto = productorepository.findById(id).get();
        producto.setEstatus(producto.getEstatus()==true ? false : true);
        productorepository.save(producto);
        return listarProducto(model, auth);
    }

    @GetMapping("/catalogoo")
    public String catalogoo(Model model, @RequestParam("email") String comercio, Authentication auth){
        System.out.println("HOLAAAAAAAAAAAAAAA"+comercio);
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(comercio);
        model.addAttribute("productos", productorepository.findByComercio(comerciosEntity));
        model.addAttribute("categorias", comerciosEntity.getCategorias());
        model.addAttribute("activo", true);
        model.addAttribute("producto", new ProductosEntity());
        metodosextra.obtUsuario(model, auth);
        return "listar/producto"; 
    }
}
