package com.mandados.Controladores;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.mandados.Entidades.Authority;
import com.mandados.Entidades.User;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.Servicios.User.IUserService;
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
        return "editarusuario";	    
    }
    @PostMapping("/editarcontrasenia")
    public String guardarDatos(@Validated User user){
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        Passgenerator ps = new Passgenerator();
        user1.setPassword(ps.getPassword(user.getPassword()));
        serviceuser.save(user1);
        sendEmail(user1.getUsername());
        return "home";
    }
    @PostMapping("/editarfoto")
    public String editarfoto(@RequestParam("file") MultipartFile imagen){
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static//logos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
                User user1 = lista.get();
            try {
                byte[] bytesImgenes = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + user1.getUsername()+".jpg");
                Files.write(rutaCompleta,bytesImgenes);
                
                user1.setImagen(user1.getUsername()+".jpg");
                serviceuser.save(user1);
            } catch (Exception e) {System.out.println(e);}
            
        }
        return "home";
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

    @GetMapping("/usuarios")
    public String listarusuario(Model model) {
        model.addAttribute("usuarios", userRepository.findAll());
        model.addAttribute("roles", authorityRepository.findAll());
        model.addAttribute("usuario", new User());
        return "listar/usuario";	    
    }
    @GetMapping("/roles")
    public String listarrol(Model model) {
        model.addAttribute("roles", authorityRepository.findAll());
        model.addAttribute("rol", new Authority());
        return "listar/authority";
    }
}
