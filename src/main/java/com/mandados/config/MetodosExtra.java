package com.mandados.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.PedidosEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
@Service
public class MetodosExtra {
    @Autowired
    public UserRepository userrepository;
    @Autowired
    public ComercioRepository comerciorepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public boolean obtCodigoPostalValido(String cp){
        String [] cps = {"68050"};
        for(int i=0;i<cps.length;i++){
            if(cp.equals(cps[i])) return true;
        }
        return false;
    }
    public void obtUsuario(Model model){
        Optional<User>lista = userrepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        User user1 = lista.get();
        model.addAttribute("usuario", user1);
        model.addAttribute("foto", "logos/"+user1.getUsername() + ".jpg");
        // model.addAttribute("comercio", comerciorepository.findByEmail(user1.getUsername()));
    }
    public String aleatorio(){
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
    public void sendEmailRemember(String correo,String generada){
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
    public void sendEmail(String correo){
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
    public List<PedidosEntity> getDatesOnListExist(List<PedidosEntity> lista){
        List<PedidosEntity> aux = new ArrayList<PedidosEntity>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getHoraEntrega()!=null){
                aux.add(lista.get(i));
            }
        }
        return aux;
    }
    public List<PedidosEntity> getDatesOnListNoExist(List<PedidosEntity> lista){
        List<PedidosEntity> aux = new ArrayList<PedidosEntity>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getHoraEntrega()!=null){
                aux.add(lista.get(i));
            }
        }
        return aux;
    }
    
    public boolean existeComercios(List<ComerciosEntity> lista, String nombre){
        for(int i=0;i<lista.size();i++){
            if(nombre.equals(lista.get(i).getNombre())) return true;
        }
        return false;
    }
    public boolean existeCategorias(List<CategoriasEntity> lista, String nombre){
        for(int i=0;i<lista.size();i++){
            if(nombre.equals(lista.get(i).getNombre())) return true;
        }
        return false;
    }
    public void sendEmail(String correo,String generada){
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
        System.out.println(generada);
        System.out.println("Send message...");
    } 
    public ComerciosEntity getComercioLogueado(Authentication authentication){
        UserDetails ud = (UserDetails)authentication.getPrincipal();
        return comerciorepository.findByEmail(ud.getUsername());
    }
}
