package com.mandados.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;
import com.mandados.Entidades.Authority;
import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Entidades.SucursalesEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.TipoComercioRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ProductoRepository;
// import com.mandados.Repository.RepartidorRepository;
import com.mandados.Servicios.Authorities.IAuthorityService;
import com.mandados.Servicios.Comercio.IComercioService;
import com.mandados.Servicios.Categoria.ICategoriaService;
import com.mandados.Servicios.Producto.IProductoService;
import com.mandados.Servicios.Repartidor.IRepartidorService;
import com.mandados.Servicios.Sucursal.ISucursalService;
import com.mandados.Servicios.User.IUserService;
import com.mandados.config.MetodosExtra;
import com.mandados.config.Passgenerator;


@Controller
@RequestMapping
public class ControladorRegistrarDatos {
    @Autowired
    private AuthorityRepository authorityrepository;
    @Autowired
    private CategoriaRepository categoriarepository;
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private ProductoRepository productorepository;
    // @Autowired
    // private RepartidorRepository repartidorrepository;
    @Autowired
    private TipoComercioRepository tipocomerciorepository;
    @Autowired
    private UserRepository userrepository;
    @Autowired
    private IAuthorityService authorityservice;
    @Autowired
    private IComercioService comercioservice;
	@Autowired
    private ICategoriaService categoriaservice;
    @Autowired
    private IProductoService productoservice;
    @Autowired
    private IRepartidorService repartidorservice;
    @Autowired
    private ISucursalService sucursalservice;
    @Autowired
    private IUserService userservice;
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private ControladorListarDatos clistar;
    //////////////// REGISTRAR COMERCIO/////////////////////
    @GetMapping("/registrocomercio")
    public String principal(Model model){
        model.addAttribute("comercio", new ComerciosEntity());
        model.addAttribute("sucursal", new SucursalesEntity());
        // System.out.println(tipocomerciorepository.findAll());
        model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
        return "registro/comercios";
    }
    @PostMapping("/registrocomercio")
    public String guardar(@Validated ComerciosEntity comercio,@Validated SucursalesEntity sucursalesEntity, ModelMap model){
        if (comercio.getNombre().equals("")||comercio.getEmail().equals("")){
            ComerciosEntity co = new ComerciosEntity();
            co.setNombre(comercio.getNombre().equals("")? "" : comercio.getNombre());
            co.setEmail(comercio.getEmail().equals("")? "" : comercio.getEmail());
            model.addAttribute("comercio", co);
            model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
            return "registro/comercios";
        }
        // System.out.println(sucursalesEntity);
        if(!metodosextra.obtCodigoPostalValido(sucursalesEntity.getCodigopostal())){
            model.addAttribute("comercio", comercio);
            model.addAttribute("sucursal", sucursalesEntity);
            model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
            model.addAttribute("codigopostalmal", true);
            return "registro/comercios";
        }
        try{
            User newuser = new User();
            newuser.setUsername(comercio.getEmail());
            newuser.setEnabled(true);
            Passgenerator ps = new Passgenerator();
            String generada = metodosextra.aleatorio();
            String password = ps.getPassword(generada);
            newuser.setPassword(password);
    
            List<Authority> lista = authorityrepository.findAll();
            ArrayList<Authority> list = new ArrayList<Authority>();
            for(int i=0;i<lista.size();i++){
                if(lista.get(i).getAuthority().equals("ROL_COMERCIO")){
                    list.add(lista.get(i));
                }
            }
            newuser.setAuthority(list);
            comercio.setTipoComercio(comercio.getTipoComercio()==null?tipocomerciorepository.findByNombre("RESTAURANTE"):comercio.getTipoComercio());
            sucursalesEntity.setNombre(comercio.getNombre());
            sucursalesEntity.setEmail(comercio.getEmail());
            try {
                newuser.setEnabled(true);
                userservice.save(newuser);
            } catch (Exception e) {
                System.out.println("ERROR AL GUARDAR");
                model.addAttribute("comercio", comercio);
                model.addAttribute("sucursal", sucursalesEntity);
                model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
                model.addAttribute("repetido", true);
                return "registro/comercios";
            }
            try {
                comercio.setEstatus(true);
                comercioservice.save(comercio);
                sucursalesEntity.setComercio(comercio);
                sucursalesEntity.setEstatus(true);
                sucursalservice.save(sucursalesEntity);
            } catch (Exception e) {
                System.out.println("ERROR AL REGISTRAR LA SUCURSAL");
                System.out.println(e);
                model.addAttribute("comercio", new ComerciosEntity());
                model.addAttribute("sucursal", new SucursalesEntity());
                model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
                return "registro/comercios";
            }
            try {
                System.out.println(comercio.getEmail());
                System.out.println(generada);
                metodosextra.sendEmail(comercio.getEmail(), generada);
            } catch (Exception e) {
                System.out.println("ERROR AL ENVIAR EMAIL");
                System.out.println(e);
            }
        }catch (Exception e){
            System.out.println("ERROR AL GUARDAR");
                System.out.println(e);
                model.addAttribute("comercio", comercio);
                model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
                model.addAttribute("repetido", true);
                return "registro/comercios";
        }
        model.addAttribute("registro",true);
        return "registro/exitoso";
    }
    //////////////// REGISTRAR REPARTIDOR/////////////////////
    @PostMapping("/registrorepartidor")
    public String registrorepartidorguardar(@Validated RepartidoresEntity rEntity, Model model){
        User user = new User();
        List<Authority> lista = authorityrepository.findAll();
        ArrayList<Authority> list = new ArrayList<Authority>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getAuthority().equals("ROL_REPARTIDOR")){
                list.add(lista.get(i));
            }
        }
        user.setAuthority(authorityrepository.findByAuthority("ROL_REPARTIDOR"));
        user.setUsername(rEntity.getEmail());
        user.setEnabled(true);
        user.setPassword(new Passgenerator().getPassword("password98"));
        try{
            userservice.save(user);
            rEntity.setEstatus(true);
            repartidorservice.save(rEntity);
            model.addAttribute("noerror", true);
        }catch(Exception e){
            model.addAttribute("error", true);
            System.out.println("ERROR AL REGISTRAR REPARTIDOR");
            System.out.println(e.getCause());
            System.out.println("///////////////////////////////////////////////////////////////////");
            System.out.println(e.getMessage());
        }
        return clistar.listarrepartidor(model);
    }
	//////////////// REGISTRAR CATEGORIA /////////////////////
    @PostMapping("/registrocategoria")
    public String registrocategoriaguardar(@Validated CategoriasEntity cEntity, Model model,Authentication auth){
        try{
            cEntity.setEstatus(true);
            categoriaservice.save(cEntity);
        }catch(Exception e){
            System.out.println("ERROR AL REGISTRAR REPARTIDOR");
            System.out.println(e);
        }
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        if(userDetail.getUsername().equals("admin")){
            model.addAttribute("categoriasseleccionadas", categoriarepository.findAll());
        }else{
            model.addAttribute("categoriasseleccionadas", comerciosEntity.getCategorias());
        }
        
        return clistar.listarcategoria(model, auth);
    }
	//////////////// ASIGNAR CATEGORIA A COMERCIO/////////////////////
    @PostMapping("/asignarcategoria")
    public String asignarcategoriaacomercio(@RequestParam("select") CategoriasEntity categorias, Model model, Authentication auth){
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        try{
            System.out.println("'/////////////////////'");
            System.out.println(categorias);
            Set<CategoriasEntity> cat = comerciosEntity.getCategorias();
            cat.add(categorias);
            comerciosEntity.setCategorias(cat);
            comerciosEntity.setEstatus(true);
            comercioservice.save(comerciosEntity);
        }catch(Exception e){
            System.out.println("ERROR AL REGISTRAR CATEGORIA");
            System.out.println(e);
        }
        return clistar.listarcategoria(model, auth);
    }
    //////////////// REGISTRAR PRODUCTO/////////////////////
    @PostMapping("/registroproducto")
    public String registroproducto(@Validated ProductosEntity producto,Model model,@RequestParam("file") MultipartFile imagen, Authentication auth){
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        producto.setComercio(comerciosEntity);
        Calendar calendario = new GregorianCalendar();
        String hora = calendario.get(Calendar.HOUR_OF_DAY) +""+ calendario.get(Calendar.MINUTE) +""+ calendario.get(Calendar.SECOND);
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static//productos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                byte[] bytesImgenes = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + producto.getNombre().split(" ")[0]+"_"+hora+".jpg");
                Files.write(rutaCompleta,bytesImgenes);
                producto.setImagen("productos/"+producto.getNombre().split(" ")[0]+"_"+hora+".jpg");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        producto.setEstatus(true);
        productoservice.save(producto);
        model.addAttribute("productos", productorepository.findByComercio(comerciosEntity));
        model.addAttribute("producto", new ProductosEntity());
        model.addAttribute("categorias", comerciosEntity.getCategorias());
        // model.addAttribute("categorias", new CategoriasEntity());
        metodosextra.obtUsuario(model);
        return "listar/producto";
    }
    //////////////// REGISTRAR AUTHORITY(ROL)/////////////////////
    @PostMapping("/registroauthority")
    public String registroauthority(@Validated Authority authority,Model model){
        // System.out.println(authority);
        authorityservice.save(authority);
        model.addAttribute("roles", authorityrepository.findAll());
        model.addAttribute("rol", new Authority());
        return "listar/authority";
    }
    //////////////// REGISTRAR USUARIO/////////////////////
    @PostMapping("/registrousuario")
    public String registrousuario(@Validated User usuario, @RequestParam("file") MultipartFile imagen, @RequestParam("select") Long select, Model model){
        System.out.println("ESTE ES EL RESULTADO =>" + select);
        Passgenerator ps = new Passgenerator();
        usuario.setPassword(ps.getPassword(usuario.getPassword()));
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static//logos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                byte[] bytesImgenes = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + usuario.getUsername()+".jpg");
                Files.write(rutaCompleta,bytesImgenes);
                usuario.setImagen(usuario.getUsername().split("@")[0]);
            } catch (Exception e) {System.out.println("/////////////////////////ERROR///////////////////////");System.out.println(e);}
        }else{
            System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////////////");
            System.out.println("////////////LA IMAGEN ESTÁ VACIA////////////////////////////");
            System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////////////");
        }
        List<Authority> lista = new ArrayList<Authority>();
        lista.add(authorityrepository.findById(select).get());
        usuario.setAuthority(lista);
        userservice.save(usuario);
        metodosextra.obtUsuario(model);
        model.addAttribute("usuarios", userrepository.findAll());
        model.addAttribute("newusuario", new User());
        return "listar/usuario";
    }
    @GetMapping("/buscarproductoo")
    public String buscarproducto(Model model, @RequestParam("group1") String seleccionado, @RequestParam("com") String comercio,String buscar){
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
        return "resultadodebusqueda";
    }
    /////////////////////// HOME///////////////////////////////
}
