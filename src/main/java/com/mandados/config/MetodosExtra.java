package com.mandados.config;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mandados.Entidades.CategoriasEntity;
import com.mandados.Entidades.ComerciosEntity;
import com.mandados.Entidades.PedidosEntity;
import com.mandados.Entidades.User;
import com.mandados.Repository.ComercioRepository;
import com.mandados.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import freemarker.template.Configuration;
import freemarker.template.Template;
@Service
public class MetodosExtra {
    @Autowired
    public UserRepository userrepository;
    @Autowired
    public ComercioRepository comerciorepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration configuration;
    
    public boolean obtCodigoPostalValido(String cp){
        String [] cps = {"68050"};
        for(int i=0;i<cps.length;i++){
            if(cp.equals(cps[i])) return true;
        }
        return false;
    }
    public void obtUsuario(Model model, Authentication auth){
        Optional<User> lista = userrepository.findByUsername(auth.getName());
        User user1 = lista.get();
        model.addAttribute("usuario", user1);
        model.addAttribute("foto", "logos/"+user1.getUsername() + ".jpg");
    }
    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    public void generarQR(String nombre){
        try {
            generateQRCodeImage("https://mandadosdevelop.herokuapp.com/spr?group1=noObtain&buscar=&com=" + nombre, 350, 350, "./src/main/resources/static/qrs/" + nombre + ".png");
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }
    public String aleatorio(){
        char [] chars = "012346789ABCDEFGHJKMNPQRSTUVWXYZ".toCharArray();
        int charsLength = chars.length;
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i=0;i<6;i++){
            buffer.append(chars[random.nextInt(charsLength)]);
        }
        return buffer.toString();
    }
    // RESET DE CONTRASEÑA
    public void sendEmailRemember(String correo,String generada){
        System.out.println("Sending message");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            Map<String, Object> model = new HashMap<>();
            model.put("correo", correo);
            model.put("contrasenia", generada);
            configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
            Template template = configuration.getTemplate("email/resetcuenta.html");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setFrom("no-reply@mandados.com");
            helper.setTo(correo);
            helper.setText(text, true);
            helper.setSubject("Hola, aquí tienes tus datos de acceso");
            javaMailSender.send(message);
            System.out.println("Send message...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo");
        }
    }
    // CAMBIO DE CONTRASEÑA
    public void sendEmail(String correo){
        System.out.println("Sending message");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            Map<String, Object> model = new HashMap<>();
            model.put("correo", correo);
            configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
            Template template = configuration.getTemplate("email/cambiocontrasenia.html");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setFrom("no-reply@mandados.com");
            helper.setTo(correo);
            helper.setText(text, true);
            helper.setSubject("¡¡¡¡ACTUALIZADO!!!!");
            javaMailSender.send(message);
            System.out.println("Send message...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo");
        }
    }
    // NUEVO COMERCIO REGISTRADO
    public void sendEmailNuevoComercio(String correo,String generada){
        System.out.println("Sending message");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            Map<String, Object> model = new HashMap<>();
            model.put("correo", correo);
            model.put("contrasenia", generada);
            model.put("nuevo", true);
            configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
            Template template = configuration.getTemplate("email/nuevocomercio.html");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setFrom("no-reply@mandados.com");
            helper.setTo(correo);
            helper.setText(text, true);
            helper.setSubject("Hola, aquí tienes tus datos de acceso");
            javaMailSender.send(message);
            System.out.println("Send message...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo");
        }
    }
    // PEDIDOS COMPLETADOS
    public List<PedidosEntity> getDatesOnListExist(List<PedidosEntity> lista){
        List<PedidosEntity> aux = new ArrayList<PedidosEntity>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getHoraentrega()==null){
                aux.add(lista.get(i));
            }
        }
        return aux;
    }
    // PEDIDOS PENDIENTES
    public List<PedidosEntity> getDatesOnListNoExist(List<PedidosEntity> lista){
        List<PedidosEntity> aux = new ArrayList<PedidosEntity>();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getHoraentrega()!=null){
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
    public ComerciosEntity getComercioLogueado(Authentication authentication){
        UserDetails ud = (UserDetails)authentication.getPrincipal();
        return comerciorepository.findByEmail(ud.getUsername());
    }
    public String crearImagenDelProducto(String nombreproducto,MultipartFile imagen){
        Calendar calendario = new GregorianCalendar();
        String hora = calendario.get(Calendar.HOUR_OF_DAY) +""+ calendario.get(Calendar.MINUTE) +""+ calendario.get(Calendar.SECOND);
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static//productos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                byte[] bytesImgenes = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + nombreproducto.split(" ")[0]+"_"+hora+".jpg");
                Files.write(rutaCompleta,bytesImgenes);
                // producto.setImagen("productos/"+producto.getNombre().split(" ")[0]+"_"+hora+".jpg");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return "productos/"+nombreproducto.split(" ")[0]+"_"+hora+".jpg";
    }
}
