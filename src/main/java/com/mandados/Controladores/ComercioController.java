package com.mandados.Controladores;

import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.SucursalesEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.AuthorityRepository;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.SucursalRepository;
import com.mandados.Repository.TipoComercioRepository;
import com.mandados.Repository.UserRepository;
import com.mandados.config.MetodosExtra;
import com.mandados.config.Passgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComercioController {
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private AuthorityRepository authorityrepository;
    @Autowired
    private ComercioRepository comerciorepository;
    @Autowired
    private SucursalRepository sucursalrepository;
    @Autowired
    private TipoComercioRepository tipocomerciorepository;
    @Autowired
    private UserRepository userrepository;
    @GetMapping("/registrocomercio")
    public String principal(Model model){
        model.addAttribute("comercio", new ComerciosEntity());
        model.addAttribute("sucursal", new SucursalesEntity());
        model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
        return "registro/comercios";
    }

    @GetMapping("/listacomercio")
    public String listarComercio(Model model, Authentication auth){
        metodosextra.obtUsuario(model, auth);
        model.addAttribute("activo", true);
        model.addAttribute("tiposcomercios", tipocomerciorepository.findAll());
        model.addAttribute("comercio", new ComerciosEntity());
        model.addAttribute("sucursal", new SucursalesEntity());
        model.addAttribute("comercios", comerciorepository.findAll());
        model.addAttribute("sucursales", sucursalrepository.findAll());
        return "listar/comercio";
    }
    @PostMapping("/registrocomerciodesdeindex")
    public String registrarComerciodesdeindex(@Validated ComerciosEntity comercio, @Validated SucursalesEntity sucursal, Model model, Authentication auth){
        if (userrepository.findByUsername(comercio.getEmail()).isEmpty()) {
            try{
                comercio.setEstatus(true);
                comerciorepository.save(comercio);
            }catch(Exception e){
                model.addAttribute("error", true);
                return "registro/comercios";
            }
            try{
                sucursal.setEstatus(true);
                sucursal.setComercio(comercio);
                sucursalrepository.save(sucursal);
            }catch(Exception e){
                comerciorepository.delete(comercio);
                model.addAttribute("error", true);
                return "registro/comercios";
            }
            Passgenerator ps = new Passgenerator();
            String generada = metodosextra.aleatorio();
            try {
                String password = ps.getPassword(generada);
                System.out.println(generada);
                User user = new User();
                user.setUsername(comercio.getEmail());
                user.setEnabled(true);
                user.setPassword(password);
                user.setAuthority(authorityrepository.findByAuthority("ROL_COMERCIO"));
                userrepository.save(user);
            } catch (Exception e) {
                sucursalrepository.delete(sucursal);
                comerciorepository.delete(comercio);
                model.addAttribute("error", true);
                return "registro/comercios";
            }
            metodosextra.generarQR(comercio.getNombre());
            metodosextra.sendEmailNuevoComercio(comercio.getEmail(), generada);
        }else{
            model.addAttribute("repetido", true);
            model.addAttribute("comercio", comercio);
            model.addAttribute("sucursal", sucursal);
            model.addAttribute("tipocomercio",tipocomerciorepository.findAll());
            return "registro/comercios";
        }
        model.addAttribute("registro", true);
        return "registro/exitoso";
    }
    @PostMapping("/registrocomercio")
    public String registrarComercio(@Validated ComerciosEntity comercio, @Validated SucursalesEntity sucursal, Model model, Authentication auth){
        try {
            comercio.setEstatus(true);
            sucursal.setEstatus(true);
            comerciorepository.save(comercio);
            sucursal.setComercio(comerciorepository.findByEmail(comercio.getEmail()));
            sucursalrepository.save(sucursal);
            Passgenerator ps = new Passgenerator();
            String generada = metodosextra.aleatorio();
            String password = ps.getPassword(generada);
            System.out.println(generada);
            User user = new User();
            user.setUsername(comercio.getEmail());
            user.setEnabled(true);
            user.setPassword(password);
            user.setAuthority(authorityrepository.findByAuthority("ROL_COMERCIO"));
            userrepository.save(user);
            metodosextra.generarQR(comercio.getNombre());
            metodosextra.sendEmailNuevoComercio(comercio.getEmail(), generada);
        } catch (Exception e) {
            System.out.println("SUCEDIO UN ERROR");
        }
        return listarComercio(model, auth);
    }
    @PostMapping("/editarcomercio{id}")
    public String editarComercio(@RequestParam("id") Long id,
                                 @RequestParam("calle") String calle, 
                                 @RequestParam("numero") String numero,
                                 @RequestParam("cp") String codigopostal,
                                 @RequestParam("colonia") String colonia,
                                 @RequestParam("telefono") String telefono,
                                  Model model, Authentication auth){
        SucursalesEntity sucursal = sucursalrepository.findById(id).get();
        sucursal.setCalle(calle);
        sucursal.setNumero(numero);
        sucursal.setCodigopostal(codigopostal);
        sucursal.setColonia(colonia);
        sucursal.setTelefono(telefono);
        sucursalrepository.save(sucursal);
        return listarComercio(model, auth);
    }
    @GetMapping("/eliminarcomercio/{id}")
	public String eliminarComercio(@PathVariable Long id, Model model, Authentication auth) {
        SucursalesEntity sucursal = sucursalrepository.findById(id).get();
        ComerciosEntity comercio = sucursalrepository.findById(id).get().getComercio();
        sucursal.setEstatus(sucursal.getEstatus()==false ? true : false);
        comercio.setEstatus(comercio.getEstatus()==false ? true : false);
        sucursalrepository.save(sucursal);
		comerciorepository.save(comercio);
		return listarComercio(model, auth);
    }
}
