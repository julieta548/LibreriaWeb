package libreriaWEB.artifactId.controladores;

import libreriaWEB.artifactId.servicios.ErrorServicio;
import libreriaWEB.artifactId.servicios.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class Portal {

    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario, @RequestParam String clave) {
        return "catalogo.html";
    }
    
    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String nombre, @RequestParam String clave) throws ErrorServicio{
        usuarioServicio.crearUsuario(nombre, clave); 
        return "login.html";
    }
    
    @GetMapping("/registro")
    public String inicio() {
        return "registro.html";
    }

    @GetMapping("/inicio")
    public String in() {
        return "inicio.html";
    }
    
    

    
}
