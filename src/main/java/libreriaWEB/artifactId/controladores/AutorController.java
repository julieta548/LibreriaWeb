package libreriaWEB.artifactId.controladores;

import java.util.Iterator;
import java.util.List;
import libreriaWEB.artifactId.entidades.Autor;
import libreriaWEB.artifactId.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import libreriaWEB.artifactId.servicios.AutorServicio;
import libreriaWEB.artifactId.servicios.ErrorServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AutorController {

    @Autowired
    AutorServicio autorServicio;

    @Autowired
    AutorRepositorio autorRepositorio;

    @GetMapping("/listado-autores")
    public String autor(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);

        return "listado-autores.html";
    }

    @GetMapping("/crear-autor")
    public String crearA() {

        return "crear-autor.html";
    }

    @PostMapping("/crear-autor")
    public String crear(@RequestParam String nombreAutor, @RequestParam String apellidoAutor) throws ErrorServicio {
        autorServicio.crearAutor(nombreAutor, apellidoAutor);

        return "redirect:/listado-autores";
    }

    @GetMapping("/modificar-autor")
    public String modificar(){
        return "modificar-autor.html";
    }
    
    @PostMapping("/modificar-autor")
    public String modificarA(@RequestParam Long id, @RequestParam String nombreAutor, @RequestParam String apellidoAutor) throws ErrorServicio {
        autorServicio.modificarAutor(id, nombreAutor, apellidoAutor);

        return "redirect:/listado-autores";
    }

    @GetMapping("/eliminar-autor/{id}")
    public String eliminarA(@PathVariable Long id) throws ErrorServicio {
        autorServicio.eliminar(id);
        return "redirect:/listado-autores";
    }

}


