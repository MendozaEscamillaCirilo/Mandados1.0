package com.mandados.Controladores;

import java.util.Set;

import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ComercioRepository;
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

@Controller
public class CategoriaController {
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private CategoriaRepository categoriarepository;
    @Autowired
    private ComercioRepository comerciorepository;
    @GetMapping("/listarcategoria")
    public String listarCategoria(Model model, Authentication auth) {
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        if((auth.getAuthorities().toArray()[0]+"").equals("ROL_COMERCIO")){
            model.addAttribute("activo", metodosextra.getComercioLogueado(auth).getEstatus());
        }else{
            model.addAttribute("activo", true);
        }
        model.addAttribute("categoria", new CategoriasEntity());
        model.addAttribute("categoriastotales", categoriarepository.findAll());
        if(userDetail.getUsername().equals("admin")){
            model.addAttribute("categoriasseleccionadas", categoriarepository.findAll());
        }else{
            model.addAttribute("categoriasseleccionadas", comerciosEntity.getCategorias());
        }
        metodosextra.obtUsuario(model, auth);
        return "listar/categoria";
    }
    @PostMapping("/registrocategoria")
    public String registrarCategoria(Model model, Authentication auth, @Validated CategoriasEntity categoria) {
        System.out.println("/////////////////////////////////////////////////////");
        System.out.println(categoria);
        System.out.println(categoria.getNombre());
        System.out.println(categoriarepository.findByNombre(categoria.getNombre()));
        System.out.println("/////////////////////////////////////////////////////");
        if(categoriarepository.findByNombre(categoria.getNombre())==null){
            try {
                categoriarepository.save(categoria);
            } catch (Exception e) {
                System.out.println("SUCEDIÓ UN ERROR => "+ e);
            }
        }else{ model.addAttribute("yaregistrado",true); }
        return listarCategoria(model, auth);
    }
    @PostMapping("/editarcategoria")
    public String editarCategoria(Model model, Authentication auth, @Validated CategoriasEntity categoria,
    @RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion) {
        CategoriasEntity categoriaaeditar = categoriarepository.findByNombre(categoria.getNombre());
        categoriaaeditar.setNombre(nombre);
        categoriaaeditar.setDescripcion(descripcion);
        categoriarepository.save(categoriaaeditar);
        return listarCategoria(model, auth);
    }
    @GetMapping(value="/eliminarcategoria/{id}")
    public String getMethodName(Model model, Authentication auth, @PathVariable Long id) {
        CategoriasEntity categoria = categoriarepository.findById(id).get();
        categoria.setEstatus(categoria.getEstatus()==false ? true : false);
        categoriarepository.save(categoria);
        return listarCategoria(model, auth);
    }
    @PostMapping("/asignarcategoria")
    public String asignarcategoriaacomercio(@RequestParam("select") CategoriasEntity categorias, Model model, Authentication auth){
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comercio =  comerciorepository.findByEmail(userDetail.getUsername());
        try{
            Set<CategoriasEntity> cat = comercio.getCategorias();
            cat.add(categorias);
            comercio.setCategorias(cat);
            comercio.setEstatus(true);
            comerciorepository.save(comercio);
        }catch(Exception e){
            System.out.println("ERROR AL REGISTRAR CATEGORIA");
            System.out.println(e);
        }
        return listarCategoria(model, auth);
    }
}
