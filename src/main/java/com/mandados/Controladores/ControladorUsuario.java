package com.mandados.Controladores;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;

import com.mandados.Entidades.Authority;
import com.mandados.Entidades.User;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.Servicios.User.IUserService;
// import com.mandados.Servicios.User.UserService;
import com.mandados.config.Passgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping
public class ControladorUsuario {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private IUserService serviceuser;
    @GetMapping("/editarusuario")
    public String editarusuario(Model model) {
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user = lista.get();
        model.addAttribute("usuario", user);
        obtUsuario(model);
        return "editarusuario";	    
    }
    @PostMapping("/editarcontrasenia")
    public String guardarDatos(@Validated User user, Model model){
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        Passgenerator ps = new Passgenerator();
        user1.setPassword(ps.getPassword(user.getPassword()));
        serviceuser.save(user1);
        sendEmail(user1.getUsername());
        obtUsuario(model);
        return "home";
    }
    @PostMapping("/editarfoto")
    public String editarfoto(@RequestParam("file") MultipartFile imagen,Model model){
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static//logos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            User user1 = lista.get();
            try {
                byte[] bytesImgenes = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + user1.getUsername()+".jpg");
                Files.write(rutaCompleta,bytesImgenes);
                user1.setImagen(user1.getUsername().split("@")[0]);
                serviceuser.save(user1);
            } catch (Exception e) {System.out.println(e);}
        }
        obtUsuario(model);
        return "home";
    }

    @PostMapping("/reset")
    public String contraseniaolvidada(@RequestParam("username") String username, Model model){
        try {
            User usuario = userRepository.findByUsername(username).get();
            Passgenerator ps = new Passgenerator();
            String generada = aleatorio();
            String password = ps.getPassword(generada);
            usuario.setPassword(password);
            try {
                serviceuser.save(usuario);
            } catch (Exception e) {
                System.out.println("ERROR AL GUARDAR");
                return "registro/exitoso";
            }
            try {
                sendEmailRemember(usuario.getUsername(), generada);
                model.addAttribute("reset",true);
                return "registro/exitoso";
            } catch (Exception e) {
                System.out.println("ERROR AL ENVIAR EMAIL");
                System.out.println(e);
            }
        } catch (Exception e) {
            model.addAttribute("existe", true);
            return "resetcontrasenia";
        }
        return "resetcontrasenia";
    }
    private void sendEmail(String correo){
        System.out.println("Sending message");
        String body = "Estimado usuario, \n    Su contraseña ah sido cambiada.. \n ";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("no-reply@mandados.com");// quien envía
        simpleMailMessage.setTo(correo);
        simpleMailMessage.setSubject("¡¡¡¡ACTUALIZADO!!!!");
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        System.out.println("Send message...");
    }
    private void sendEmailRemember(String correo,String generada){
        System.out.println("Sending message");
        String body = "Estimado usuario, \n     \n " +
                "   Los datos de acceso son: \n     EMAIL: " + correo +" \n " + 
                "    CONTRASEÑA: " + generada;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("no-reply@mandados.com");// quien envía
        simpleMailMessage.setTo(correo);
        simpleMailMessage.setSubject("¡¡¡¡GRACIAS!!!!");
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        System.out.println("Send message...");
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
    public void obtUsuario(Model model){
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        model.addAttribute("usuario", user1);
        model.addAttribute("foto", "logos/"+user1.getUsername() + ".jpg");
    }

    @GetMapping("/usuarios")
    public String listarusuario(Model model) {
        model.addAttribute("usuarios", userRepository.findAll());
        model.addAttribute("roles", authorityRepository.findAll());
        model.addAttribute("usuario", new User());
        obtUsuario(model);
        return "listar/usuario";	    
    }
    @GetMapping("/roles")
    public String listarrol(Model model) {
        model.addAttribute("roles", authorityRepository.findAll());
        model.addAttribute("rol", new Authority());
        obtUsuario(model);
        return "listar/authority";
    }
}
