package com.mandados.Controladores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.config.MetodosExtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class ControladorBuscar {
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private ControladorListarDatos clistar;
    @Autowired
    private CategoriaRepository categoriarepository;
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private ProductoRepository productorepository;
    ////////////////////Buscar productos desde el index////////////////
    @GetMapping("/spr")
    public String buscarProductosDesdeIndex(Model model, @RequestParam("group1") String seleccionado, @RequestParam("com") String comercio,String buscar){
        List<ProductosEntity>productos = productorepository.findByNombreContaining(buscar);
        List<CategoriasEntity>categorias = new ArrayList<CategoriasEntity>();
        List<ComerciosEntity>comercios = new ArrayList<ComerciosEntity>();
        for(int i=0;i<productos.size();i++){
            if(comercios.size()==0){
                comercios.add(comerciorepository.findByEmail(productos.get(i).getComercio().getEmail()));
            }
            if (!metodosextra.existeComercios(comercios, productos.get(i).getComercio().getNombre())) {
                comercios.add(comerciorepository.findByEmail(productos.get(i).getComercio().getEmail()));
            }
        }
        if(!comercio.equals("no")){
            productos = productorepository.findByComercioAndNombreContaining(comerciorepository.findByNombre(comercio), buscar);
            model.addAttribute("productos", productos);
            for(int i=0;i<productos.size();i++){
                if(categorias.size()==0){
                    categorias.add(categoriarepository.findByNombre(productos.get(i).getCategoria().getNombre()));
                }
                if (!metodosextra.existeCategorias(categorias, productos.get(i).getCategoria().getNombre())) {
                    categorias.add(categoriarepository.findByNombre(productos.get(i).getCategoria().getNombre()));
                }
            }
            System.out.println(categorias.size());
            model.addAttribute("unacategoria", categorias.size()>1 ? true : false);
        }else{
            model.addAttribute("productos", productos);
        }
        model.addAttribute("seleccionado", seleccionado);
        model.addAttribute("categorias", categorias);
        model.addAttribute("comercios", comercios);
        model.addAttribute("buscar", buscar);
        if(seleccionado.equals("noObtain")){
            model.addAttribute("porproducto", true);
        }else{
            model.addAttribute("porcomercio", true);
        }
        return "resultadosdebusqueda";
    }
    ////////////////////Buscar comercios desde el home////////////////
    @GetMapping("/gp")
    public String buscarComerciosDesdeHome(Model model, Authentication auth, @RequestParam("search") String producto){
        model.addAttribute("comen",true);
        model.addAttribute("tablavisible", true);
        model.addAttribute("search", producto);
        List<ProductosEntity>productos = productorepository.findByNombreContaining(producto);
        List<ComerciosEntity>comercios = new ArrayList<ComerciosEntity>();
        for(int i=0;i<productos.size();i++){
            if(comercios.size()==0){
                comercios.add(comerciorepository.findByEmail(productos.get(i).getComercio().getEmail()));
            }
            if (!metodosextra.existeComercios(comercios, productos.get(i).getComercio().getNombre())) {
                comercios.add(comerciorepository.findByEmail(productos.get(i).getComercio().getEmail()));
            }
        }
        model.addAttribute("comercios", comercios);
        model.addAttribute("busqueda", false);
        // Set<ProductosEntity> auxiliar = 
        // Set<ProductosEntity> productos = (Set<ProductosEntity>)productorepository.findByNombreContaining(producto);
        // model.addAttribute("comercios", comerciorepository.findByProductos(productos));
        // model.addAttribute("productos", productorepository.findByNombreContaining(producto));
        return clistar.home(auth, model);
    }
    @GetMapping("/bpdh")
    public String buscarProductosDesdeHome(Model model, Authentication auth, @RequestParam("search") String producto, @RequestParam("idp") Long idp){
        model.addAttribute("comen",true);//ACA TENGO DUDA SI LES DEJO ESTA TABLA O MEJOR SE LAS QUITO, ES DONDE APARECEN LOS COMERCIOS
        model.addAttribute("proen",true);
        model.addAttribute("tablavisible", true);
        model.addAttribute("search", producto);
        List<ProductosEntity>productos = productorepository.findByNombreContaining(producto);
        List<ComerciosEntity>comercios = new ArrayList<ComerciosEntity>();
        for(int i=0;i<productos.size();i++){
            if(comercios.size()==0){
                comercios.add(comerciorepository.findByEmail(productos.get(i).getComercio().getEmail()));
            }
            if (!metodosextra.existeComercios(comercios, productos.get(i).getComercio().getNombre())) {
                comercios.add(comerciorepository.findByEmail(productos.get(i).getComercio().getEmail()));
            }
        }
        model.addAttribute("comercios", comercios);
        model.addAttribute("busqueda", false);
        model.addAttribute("search", producto);
        model.addAttribute("productos", productorepository.findByComercio(comerciorepository.findById(idp).get()));
        return clistar.home(auth, model);
    }
    @GetMapping("/obtcomercios")
    public String agregaralcarrito(Model model, Authentication auth){
        List<ComerciosEntity>lista = comerciorepository.findAll();
        Iterator<ComerciosEntity> it = lista.iterator();
        String respuesta = "[";
        while (it.hasNext()) {
            ComerciosEntity comercio = it.next();
            respuesta += "{ nombre: '" + comercio.getNombre()+"', email: '"+comercio.getEmail()+"' },";
        }
        respuesta += "{}]";
        return respuesta;
    }
}
