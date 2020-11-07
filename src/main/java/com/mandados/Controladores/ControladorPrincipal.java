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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
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
        if(!sucursalesEntity.getCodigopostal().equals("68050")){
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
                sucursalservice.save(sucursalesEntity);
                servicecomercio.save(comercio);
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
    public String registrocategoriaguardar(@Validated CategoriasEntity cEntity, Model model){
        try{
            categoriaservice.save(cEntity);
        }catch(Exception e){
            System.out.println("ERROR AL REGISTRAR REPARTIDOR");
            System.out.println(e);
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
        String hora = GregorianCalendar.MONTH +""+ GregorianCalendar.HOUR +""+ GregorianCalendar.MINUTE +""+ GregorianCalendar.SECOND;
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
        model.addAttribute("productos", productorepository.findAll());
        model.addAttribute("producto", new ProductosEntity());
        model.addAttribute("categorias", new CategoriasEntity());
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
        char [] chars = "0123456789ABCDEFGHJKMNPQRSTUVWXYZ".toCharArray();
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
    public String buscarproducto(Model model, @RequestParam("group1") String seleccionado,String buscar){
        // List<ProductosEntity> productos = new ArrayList<ProductosEntity>();
        // List<ProductosEntity> productosactuales = productorepository.findAll();
        // for(int i=0;i<productosactuales.size();i++){

        // }
        model.addAttribute("seleccionado", seleccionado);
        model.addAttribute("productos", productorepository.findAll());
        model.addAttribute("categorias", categoriarepository.findAll());
        return "resultadodebusqueda";
    }
    public void obtUsuario(Model model){
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        model.addAttribute("usuario", user1);
        model.addAttribute("foto", "logos/"+user1.getUsername() + ".jpg");
        model.addAttribute("comercio", comerciorepository.findByEmail(user1.getUsername()));
    }
}
