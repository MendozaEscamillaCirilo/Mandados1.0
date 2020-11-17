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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.mail.SimpleMailMessage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import com.mandados.Entidades.Authority;
import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.PedidosEntity;
import com.mandados.Entidades.ProductosEntity;
import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Entidades.SucursalesEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.TipoComercioRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.PedidoRepository;
import com.mandados.Repository.CategoriaRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.Servicios.Authorities.IAuthorityService;
import com.mandados.Servicios.Comercio.IComercioService;
import com.mandados.Servicios.Categoria.ICategoriaService;
import com.mandados.Servicios.Producto.IProductoService;
import com.mandados.Servicios.Repartidor.IRepartidorService;
import com.mandados.Servicios.Sucursal.ISucursalService;
import com.mandados.Servicios.User.IUserService;
import com.mandados.config.Passgenerator;

import java.util.Random;

@Controller
@RequestMapping
public class ControladorPrincipal {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private CategoriaRepository categoriarepository;
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private PedidoRepository pedidorepository;
    @Autowired
    private ProductoRepository productorepository;
    @Autowired
    private RepartidorRepository repartidorRepository;
    @Autowired
    private TipoComercioRepository tipocomerciorepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IAuthorityService authorityservice;
    @Autowired
    private IComercioService servicecomercio;
	@Autowired
    private ICategoriaService categoriaservice;
    @Autowired
    private IProductoService productoservice;
    @Autowired
    private IRepartidorService repartidorservice;
    @Autowired
    private ISucursalService sucursalservice;
    @Autowired
    private IUserService serviceuser;
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
        if(!obtCodigoPostalValido(sucursalesEntity.getCodigopostal())){
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
            String generada = aleatorio();
            String password = ps.getPassword(generada);
            newuser.setPassword(password);
    
            List<Authority> lista = authorityRepository.findAll();
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
                serviceuser.save(newuser);
            } catch (Exception e) {
                System.out.println("ERROR AL GUARDAR");
                model.addAttribute("comercio", comercio);
                model.addAttribute("sucursal", sucursalesEntity);
                model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
                model.addAttribute("repetido", true);
                return "registro/comercios";
            }
            try {
                servicecomercio.save(comercio);
                sucursalesEntity.setComercio(comercio);
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
                sendEmail(comercio.getEmail(), generada);
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
        List<Authority> lista = authorityRepository.findAll();
        ArrayList<Authority> list = new ArrayList<Authority>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getAuthority().equals("ROL_REPARTIDOR")){
                list.add(lista.get(i));
            }
        }
        user.setAuthority(list);
        user.setUsername(rEntity.getEmail());
        user.setEnabled(true);
        user.setPassword(new Passgenerator().getPassword("password98"));
        try{
            repartidorservice.save(rEntity);
            serviceuser.save(user);
        }catch(Exception e){
            System.out.println("ERROR AL REGISTRAR REPARTIDOR");
            System.out.println(e);
        }
        model.addAttribute("repartidores", repartidorRepository.findAll());
        model.addAttribute("repartidor", new RepartidoresEntity());
        obtUsuario(model);
        return "listar/repartidor";
    }
	//////////////// REGISTRAR CATEGORIA /////////////////////
    @PostMapping("/registrocategoria")
    public String registrocategoriaguardar(@Validated CategoriasEntity cEntity, Model model,Authentication auth){
        try{
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
        model.addAttribute("categorias", categoriarepository.findAll());
        model.addAttribute("categoria", new CategoriasEntity());
        obtUsuario(model);
        return "listar/categoria";
    }
	//////////////// ASIGNAR CATEGORIA A COMERCIO/////////////////////
    @PostMapping("/asignarcategoria")
    public String asignarcategoriaacomercio(@RequestParam("select") CategoriasEntity categorias, Model model, Authentication auth){
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        ComerciosEntity comerciosEntity =  comerciorepository.findByEmail(userDetail.getUsername());
        try{
            System.out.println(categorias);
            // List<CategoriasEntity> cat = (comerciosEntity.getCategorias().size()!=0)?comerciosEntity.getCategorias() : new ArrayList<CategoriasEntity>();
            List<CategoriasEntity> cat = new ArrayList<CategoriasEntity>();
            if (comerciosEntity.getCategorias().size()>0) {
                for(int i=0;i<comerciosEntity.getCategorias().size();i++){
                    cat.add(comerciosEntity.getCategorias().get(i));
                }
            }
            cat.add(categorias);
            comerciosEntity.setCategorias(cat);
            servicecomercio.save(comerciosEntity);
        }catch(Exception e){
            System.out.println("ERROR AL REGISTRAR CATEGORIA");
            System.out.println(e);
        }
        // ///////////////////////////
        obtUsuario(model);
        model.addAttribute("categoriastotales", categoriarepository.findAll());
        model.addAttribute("categoriasseleccionadas", comerciosEntity.getCategorias());
        model.addAttribute("categoria", new CategoriasEntity());
        return "listar/categoria";
        // ///////////////////////////
        // return "";
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
        
        productoservice.save(producto);
        model.addAttribute("productos", productorepository.findByComercio(comerciosEntity));
        model.addAttribute("producto", new ProductosEntity());
        model.addAttribute("categorias", comerciosEntity.getCategorias());
        // model.addAttribute("categorias", new CategoriasEntity());
        obtUsuario(model);
        return "listar/producto";
    }
    //////////////// REGISTRAR AUTHORITY(ROL)/////////////////////
    @PostMapping("/registroauthority")
    public String registroauthority(@Validated Authority authority,Model model){
        // System.out.println(authority);
        authorityservice.save(authority);
        model.addAttribute("roles", authorityRepository.findAll());
        model.addAttribute("rol", new Authority());
        return "listar/authority";
    }
    //////////////// REGISTRAR USUARIO/////////////////////
    @PostMapping("/registrousuario")
    public String registrousuario(Model model){
        return "home";
    }
    private String aleatorio(){
        char [] chars = "012346789ABCDEFGHJKMNPQRSTUVWXYZ".toCharArray();
        int charsLength = chars.length;
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i=0;i<6;i++){
            buffer.append(chars[random.nextInt(charsLength)]);
        }
        // System.out.println("Random String " + buffer.toString());
        return buffer.toString();
    }
    private void sendEmail(String correo,String generada){
        System.out.println("Sending message");
        String body = "Estimado usuario, \n    Gracias por contactarnos al correo \n " +
                "   Los datos de acceso son: \n     USUARIO: El correo que usó en el registro.\n " + 
                "    CONTRASEÑA: " + generada;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("no-reply@mandados.com");// quien envía
        simpleMailMessage.setTo(correo);
        simpleMailMessage.setSubject("¡¡¡¡GRACIAS!!!!");
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        System.out.println("Send message...");
    }
    @GetMapping("/buscarproductoo")
    public String buscarproducto(Model model, @RequestParam("group1") String seleccionado, @RequestParam("com") String comercio,String buscar){
        List<ProductosEntity>productos = productorepository.findByNombreContaining(buscar);
        List<CategoriasEntity>categorias = new ArrayList<CategoriasEntity>();
        List<ComerciosEntity>comercios = new ArrayList<ComerciosEntity>();
        for(int i=0;i<productos.size();i++){
            if(comercios.size()==0){
                comercios.add(comerciorepository.findByNombre(productos.get(i).getComercio().getNombre()));
            }
            if (!existeComercios(comercios, productos.get(i).getComercio().getNombre())) {
                comercios.add(comerciorepository.findByNombre(productos.get(i).getComercio().getNombre()));
            }
        }
        if(!comercio.equals("no")){
            productos = productorepository.findByComercioAndNombreContaining(comerciorepository.findByNombre(comercio), buscar);
            model.addAttribute("productos", productos);
            for(int i=0;i<productos.size();i++){
                if(categorias.size()==0){
                    categorias.add(categoriarepository.findByNombre(productos.get(i).getCategoria().getNombre()));
                }
                if (!existeCategorias(categorias, productos.get(i).getCategoria().getNombre())) {
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
    private boolean existeCategorias(List<CategoriasEntity> lista, String nombre){
        for(int i=0;i<lista.size();i++){
            if(nombre.equals(lista.get(i).getNombre())) return true;
        }
        return false;
    }
    private boolean existeComercios(List<ComerciosEntity> lista, String nombre){
        for(int i=0;i<lista.size();i++){
            if(nombre.equals(lista.get(i).getNombre())) return true;
        }
        return false;
    }
    public void obtUsuario(Model model){
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        model.addAttribute("usuario", user1);
        model.addAttribute("foto", "logos/"+user1.getUsername() + ".jpg");
        model.addAttribute("comercio", comerciorepository.findByEmail(user1.getUsername()));
    }
    private boolean obtCodigoPostalValido(String cp){
        String [] cps = {"68050"};
        for(int i=0;i<cps.length;i++){
            if(cp.equals(cps[i])) return true;
        }
        return false;
    }
    private List<PedidosEntity> getDatesOnListNoExist(List<PedidosEntity> lista){
        List<PedidosEntity> aux = new ArrayList<PedidosEntity>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getHora_entrega()!=null){
                aux.add(lista.get(i));
            }
        }
        return aux;
    }
    private List<PedidosEntity> getDatesOnListExist(List<PedidosEntity> lista){
        List<PedidosEntity> aux = new ArrayList<PedidosEntity>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getHora_entrega()!=null){
                aux.add(lista.get(i));
            }
        }
        return aux;
    }

    /////////////////////// HOME///////////////////////////////
    @GetMapping("/home")
    public String userPage(Authentication authentication, Model model) {
        model.addAttribute("totalcomercios", comerciorepository.count());
        model.addAttribute("totalrepartidores", repartidorRepository.count());
        model.addAttribute("ordenescompletadas", getDatesOnListNoExist(pedidorepository.findAll()).size());
        model.addAttribute("ordenespendientes", getDatesOnListExist(pedidorepository.findAll()).size());
        obtUsuario(model);
        return "home";	    
    }
}
