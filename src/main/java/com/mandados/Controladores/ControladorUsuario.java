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
import com.mandados.config.MetodosExtra;
import com.mandados.config.Passgenerator;

import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private IUserService serviceuser;
    @Autowired
    private MetodosExtra metodosextra;
    @GetMapping("/editarusuario")
    public String editarusuario(Model model) {
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user = lista.get();
        model.addAttribute("usuario", user);
        metodosextra.obtUsuario(model);
        return "editarusuario";	    
    }
    @PostMapping("/editarcontrasenia")
    public String guardarDatos(@Validated User user, Model model){
        Optional<User>lista = userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        Passgenerator ps = new Passgenerator();
        user1.setPassword(ps.getPassword(user.getPassword()));
        serviceuser.save(user1);
        metodosextra.sendEmail(user1.getUsername());
        metodosextra.obtUsuario(model);
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
        metodosextra.obtUsuario(model);
        return "home";
    }
    @PostMapping("/reset")
    public String contraseniaolvidada(@RequestParam("username") String username, Model model){
        try {
            User usuario = userRepository.findByUsername(username).get();
            Passgenerator ps = new Passgenerator();
            String generada = metodosextra.aleatorio();
            String password = ps.getPassword(generada);
            usuario.setPassword(password);
            try {
                serviceuser.save(usuario);
            } catch (Exception e) {
                System.out.println("ERROR AL GUARDAR");
                return "registro/exitoso";
            }
            try {
                metodosextra.sendEmailRemember(usuario.getUsername(), generada);
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
    @GetMapping("/usuarios")
    public String listarusuario(Model model) {
        model.addAttribute("usuarios", userRepository.findAll());
        model.addAttribute("roles", authorityRepository.findAll());
        model.addAttribute("newusuario", new User());
        model.addAttribute("activo", true);
        metodosextra.obtUsuario(model);
        return "listar/usuario";	    
    }
    @GetMapping("/roles")
    public String listarrol(Model model) {
        model.addAttribute("roles", authorityRepository.findAll());
        model.addAttribute("rol", new Authority());
        model.addAttribute("activo", true);
        metodosextra.obtUsuario(model);
        return "listar/authority";
    }
    
    
}
