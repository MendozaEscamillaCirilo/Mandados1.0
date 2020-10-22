package com.mandados.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.mandados.Entidades.Authority;
import com.mandados.Entidades.Restaurante;
import com.mandados.Entidades.User;
import com.mandados.Entidades.Repartidor;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Restaurante.IRestauranteService;
import com.mandados.Repartidor.IRepartidorService;
import com.mandados.User.IUserService;
import com.mandados.config.Passgenerator;

import java.util.ArrayList;
import java.util.List;
import  java.util.Random;
import com.mandados.Email.EmailService;

@Controller
@RequestMapping
public class ControladorPrincipal {

    @Autowired
    private IRestauranteService servicerestaurante;

    @Autowired
    private IUserService serviceuser;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private IRepartidorService servicerepartidor;
    
    @GetMapping("/resreg")
    public String principal(Model model){
        model.addAttribute("userrestaurante", new Restaurante());
        model.addAttribute("correo", false);
        return "registro/restaurante";
    }

    @PostMapping("/register_restaurante")
	public String save(@Validated Restaurante r, Model model){
        try{
            User newuserrestaurante = new User();
            newuserrestaurante.setUsername(r.getCorreo());
            newuserrestaurante.setEnabled(true);
            Passgenerator ps = new Passgenerator();
            String generada = aleatorio();
            String password = ps.getPassword(generada);
            newuserrestaurante.setPassword(password);

            List<Authority> lista = authorityRepository.findAll();
            ArrayList<Authority> list = new ArrayList<Authority>();
            for(int i=0;i<lista.size();i++){
                if(lista.get(i).getAuthority().equals("ROL_RESTAURANTE")){
                    list.add(lista.get(i));
                    // System.out.println("Respuesta => " + lista.get(i).getAuthority());
                }
            }
            newuserrestaurante.setAuthority(list);
            serviceuser.save(newuserrestaurante);
            servicerestaurante.save(r);
            sendEmail(r.getCorreo(), generada);
        }catch (Exception exception){
            model.addAttribute("userrestaurante", r);
            model.addAttribute("correo", true);
            return "registro/restaurante";
        }
        
		return "redirect:/";
    }

    @GetMapping("/resrep")
    public String registroRepartidor(Model model){
        model.addAttribute("userrepartidor", new Repartidor());
        model.addAttribute("correo", false);
        return "registro/repartidor";
    }

    @PostMapping("/register_repartidor")
	public String saveRepartidor(@Validated Repartidor rep, Model model){
        
        User newuserrepartidor = new User();
        try {
            newuserrepartidor.setUsername(rep.getCorreo());
            newuserrepartidor.setEnabled(true);
            Passgenerator ps = new Passgenerator();
            String generada = aleatorio();
            String password = ps.getPassword(generada);
            newuserrepartidor.setPassword(password);

            List<Authority> lista = authorityRepository.findAll();
            ArrayList<Authority> list = new ArrayList<Authority>();
            for(int i=0;i<lista.size();i++){
                if(lista.get(i).getAuthority().equals("ROL_REPARTIDOR")){
                    list.add(lista.get(i));
                    // System.out.println("Respuesta => " + lista.get(i).getAuthority());
                }
            }
            newuserrepartidor.setAuthority(list);
            // // System.out.println("List => " + list);
            
            serviceuser.save(newuserrepartidor);
            servicerepartidor.save(rep);
            sendEmail(rep.getCorreo(), generada);
        } catch (Exception e) {
            System.out.println(rep);
            Repartidor re = new Repartidor();
            re.setNombre(rep.getNombre());
            re.setTelefono(rep.getTelefono());
            model.addAttribute("userrepartidor", re);
            model.addAttribute("correo", true);
            return "registro/repartidor";
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
        String body = "Estimado usuario, \n  Gracias por contactarnos al correo \n " +
                "Los datos de acceso son: \n user: El correo que usó en el registro.\n " + 
                "contraseña: " + generada;
                emailService.sendEmail(correo, body, "¡¡¡¡GRACIAS!!!!");
    }
}
