package com.mandados.Controladores;

import com.mandados.Entidades.RepartidoresEntity;
import com.mandados.Repository.RepartidorRepository;
import com.mandados.config.MetodosExtra;

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
public class RepartidorController {
    @Autowired
    private MetodosExtra metodosextra;
    @Autowired
    private RepartidorRepository repartidorrepository;
    @GetMapping("/listarepartidor")
    public String listarRepartidor(Model model, Authentication auth) {
        model.addAttribute("repartidores", repartidorrepository.findAll());
        model.addAttribute("repartidor", new RepartidoresEntity());
        model.addAttribute("activo", true);
        metodosextra.obtUsuario(model, auth);
        return "listar/repartidor";
    }
    @PostMapping(value="/registrorepartidor")
    public String registrarRepartidor(Model model, Authentication auth,@Validated RepartidoresEntity repartidor) {
        repartidor.setEstatus(true);
        repartidorrepository.save(repartidor);
        return listarRepartidor(model, auth);
    }
    @PostMapping(value="/editarrepartidor")
    public String editarRepartidor(Model model, Authentication auth,@RequestParam("id") Long id,
                                   @RequestParam("nombre") String nombre, 
                                   @RequestParam("primerapellido") String primerapellido,
                                   @RequestParam("segundoapellido") String segundoapellido,
                                   @RequestParam("telefono") String telefono,
                                   @RequestParam("calle") String calle,
                                   @RequestParam("numero") String numero,
                                   @RequestParam("colonia") String colonia,
                                   @RequestParam("municipio") String municipio) {
        RepartidoresEntity repartidor = repartidorrepository.findById(id).get();
        repartidor.setNombre(nombre);
        repartidor.setPrimerapellido(primerapellido);
        repartidor.setSegundoapellido(segundoapellido);
        repartidor.setTelefono(telefono);
        repartidor.setCalle(calle);
        repartidor.setNumero(numero);
        repartidor.setColonia(colonia);
        repartidor.setMunicipio(municipio);
        repartidorrepository.save(repartidor);
        return listarRepartidor(model, auth);
    }
    @GetMapping("/eliminarrepartidor/{id}")
	public String eliminarRepartidor(@PathVariable Long id, Model model, Authentication auth) {
        RepartidoresEntity repartidor = repartidorrepository.findById(id).get();
        repartidor.setEstatus(repartidor.getEstatus()==false ? true : false);
		repartidorrepository.save(repartidor);
		return listarRepartidor(model, auth);
	}
}