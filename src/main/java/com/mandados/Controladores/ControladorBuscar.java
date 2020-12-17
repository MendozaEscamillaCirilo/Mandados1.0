package com.mandados.Controladores;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
    ////////////////////Buscar productos desde el home////////////////
    @GetMapping("/gp")
    public String buscarProductosDesdeHome(Model model, Authentication auth){
        return clistar.home(auth, model);
    }
}
