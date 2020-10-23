package com.mandados.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.mandados.Entidades.Authority;
// import com.mandados.Entidades.Restaurante;
import com.mandados.Entidades.User;
// import com.mandados.Entidades.Repartidor;
import com.mandados.Repository.AuthorityRepository;
// import com.mandados.Restaurante.IRestauranteService;
// import com.mandados.Repartidor.IRepartidorService;
import com.mandados.User.IUserService;
import com.mandados.config.Passgenerator;

import java.util.ArrayList;
import java.util.List;
import  java.util.Random;

import javax.validation.Valid;

import com.mandados.Email.EmailService;

@Controller
@RequestMapping
public class ControladorPrincipal {

    // @Autowired
    // private IRestauranteService servicerestaurante;

    // @Autowired
    // private IUserService serviceuser;

    // @Autowired
    // private EmailService emailService;
    
    @Autowired
    private AuthorityRepository authorityRepository;

    // @Autowired
    // private IRepartidorService servicerepartidor;
    
    // @GetMapping("/resreg")
    // public String principal(Model model){
    //     model.addAttribute("userrestaurante", new Restaurante());
    //     model.addAttribute("correo", false);
    //     return "registro/restaurante";
    // }

    // @PostMapping("/resreg")
	// public String save(@Validated Restaurante r, Model model){
    //     if (r.getNombre().equals("")||r.getCorreo().equals("")||r.getCalle().equals("")||r.getColonia().equals("")||r.getNumero_casa().equals("")||r.getReferencia_domicilio().equals("")) {
    //         Restaurante re = new Restaurante();
    //         re.setNombre(re.getNombre().equals("")? "" : re.getNombre());
    //         re.setCorreo(re.getCorreo().equals("")? "" : re.getCorreo());
    //         re.setCalle(re.getCalle().equals("")?"":re.getCalle());
    //         re.setColonia(re.getColonia().equals("")?"":re.getColonia());
    //         re.setNumero_casa(re.getNumero_casa().equals("")?"":re.getNumero_casa());
    //         re.setReferencia_domicilio(re.getReferencia_domicilio().equals("")?"":re.getReferencia_domicilio());

    //         model.addAttribute("userrestaurante", re);
    //         model.addAttribute("nombre", r.getNombre().equals("")? true : false);
    //         model.addAttribute("correo", r.getCorreo().equals("")? true : false);
    //         model.addAttribute("calle", r.getCalle().equals("")? true : false);
    //         model.addAttribute("colonia", r.getColonia().equals("")? true : false);
    //         model.addAttribute("numero_casa", r.getNumero_casa().equals("")? true : false);
    //         model.addAttribute("referencia", r.getReferencia_domicilio().equals("")? true : false);
    //         return "registro/restaurante";
    //     }
    //     try{
    //         User newuserrestaurante = new User();
    //         newuserrestaurante.setUsername(r.getCorreo());
    //         newuserrestaurante.setEnabled(true);
    //         Passgenerator ps = new Passgenerator();
    //         String generada = aleatorio();
    //         String password = ps.getPassword(generada);
    //         newuserrestaurante.setPassword(password);

    //         List<Authority> lista = authorityRepository.findAll();
    //         ArrayList<Authority> list = new ArrayList<Authority>();
    //         for(int i=0;i<lista.size();i++){
    //             if(lista.get(i).getAuthority().equals("ROL_RESTAURANTE")){
    //                 list.add(lista.get(i));
    //             }
    //         }
    //         newuserrestaurante.setAuthority(list);
    //         serviceuser.save(newuserrestaurante);
    //         servicerestaurante.save(r);
    //         sendEmail(r.getCorreo(), generada);
    //     }catch (Exception exception){
    //         model.addAttribute("userrestaurante", r);
    //         model.addAttribute("repetido", true);
    //         return "registro/restaurante";
    //     }
	// 	return "redirect:/";
    // }

    // @GetMapping("/resrep")
    // public String registroRepartidor(Model model){
    //     model.addAttribute("userrepartidor", new Repartidor());
    //     model.addAttribute("correo", false);
    //     return "registro/repartidor";
    // }

    // @PostMapping("/resrep")
	// public String saveRepartidor(@ModelAttribute @Valid Repartidor rep, Model model, BindingResult bindingResult){
    //     if (rep.getNombre().equals("")||rep.getCorreo().equals("")||rep.getTelefono().equals("")) {
    //         Repartidor re = new Repartidor();
    //         re.setNombre(rep.getNombre().equals("")? "" : rep.getNombre());
    //         re.setCorreo(rep.getCorreo().equals("")? "" : rep.getCorreo());
    //         re.setTelefono(rep.getTelefono().equals("")? "" : rep.getTelefono());
    //         model.addAttribute("userrepartidor", re);
    //         model.addAttribute("nombre", rep.getNombre().equals("")? true : false);
    //         model.addAttribute("correo", rep.getCorreo().equals("")? true : false);
    //         model.addAttribute("telefono", rep.getTelefono().equals("")? true : false);
    //         return "registro/repartidor";
    //     }
    //     try {
    //         User newuserrepartidor = new User();
    //         newuserrepartidor.setUsername(rep.getCorreo());
    //         newuserrepartidor.setEnabled(true);
    //         Passgenerator ps = new Passgenerator();
    //         String generada = aleatorio();
    //         String password = ps.getPassword(generada);
    //         newuserrepartidor.setPassword(password);
    //         List<Authority> lista = authorityRepository.findAll();
    //         ArrayList<Authority> list = new ArrayList<Authority>();
    //         for(int i=0;i<lista.size();i++){ if(lista.get(i).getAuthority().equals("ROL_REPARTIDOR")){ list.add(lista.get(i)); }}
    //         newuserrepartidor.setAuthority(list);
    //         serviceuser.save(newuserrepartidor);
    //         servicerepartidor.save(rep);
    //         sendEmail(rep.getCorreo(), generada);
    //     } catch (Exception e) {
    //         model.addAttribute("userrepartidor", rep);
    //         model.addAttribute("repetido", true);
    //         return "registro/repartidor";
    //     }
	// 	return "redirect:/";
    // }
    
    // private String aleatorio(){
    //     char [] chars = "0123456789ABCDEFGHJKMNPQRSTUVWXYZ".toCharArray();
    //     int charsLength = chars.length;
    //     Random random = new Random();
    //     StringBuffer buffer = new StringBuffer();
    //     for (int i=0;i<6;i++){
    //         buffer.append(chars[random.nextInt(charsLength)]);
    //     }
    //     // System.out.println("Random String " + buffer.toString());
    //     return buffer.toString();
    // }
    // private void sendEmail(String correo,String generada){
    //     String body = "Estimado usuario, \n  Gracias por contactarnos al correo \n " +
    //             "Los datos de acceso son: \n user: El correo que usó en el registro.\n " + 
    //             "contraseña: " + generada;
    //             emailService.sendEmail(correo, body, "¡¡¡¡GRACIAS!!!!");
    // }
}
