package com.mandados.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;
import com.mandados.Entidades.Authority;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.SucursalesEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.TipoComercioRepository;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.PedidoRepository;
import com.mandados.Repository.ProductoRepository;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.Servicios.Comercio.IComercioService;
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
    private IComercioService servicecomercio;
    @Autowired
    private TipoComercioRepository tipocomerciorepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private RepartidorRepository repartidorRepository;
    @Autowired
    private PedidoRepository pedidorepository;
    @Autowired
    private ProductoRepository productorepository;
    @Autowired
    private IUserService serviceuser;
    @Autowired
    private ISucursalService sucursalservice;

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
                sucursalservice.save(sucursalesEntity);
                serviceuser.save(newuser);
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
            
            }catch (Exception exception){
                System.out.println("ERROR AL GUARDAR");
                System.out.println(exception);
                model.addAttribute("comercio", comercio);
                model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
                model.addAttribute("repetido", true);
                return "registro/comercios";
            }
        return "redirect:/";
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

    @GetMapping("/listarepartidor")
    public String listarrepartidor(Model model) {
        model.addAttribute("repartidores", repartidorRepository.findAll());
        return "listar/repartidor";	    
    }
    @GetMapping("/listaorden")
    public String listarorden(Model model) {
        model.addAttribute("pedidos", pedidorepository.findAll());
        return "listar/orden";	    
    }
    @GetMapping("/listaproducto")
    public String listarproducto(Model model) {
        model.addAttribute("productos", productorepository.findAll());
        return "listar/producto";	    
    }
    @GetMapping("/listacatalogo")
    public String listarcatalogo(Model model) {
        // model.addAttribute("comercios", comerciorepository.findAll());
        return "listar/carta";	    
    }
}
