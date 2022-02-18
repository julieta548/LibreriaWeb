

package libreriaWEB.artifactId.controladores;

import java.util.Iterator;
import java.util.List;
import libreriaWEB.artifactId.entidades.Editorial;
import libreriaWEB.artifactId.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import libreriaWEB.artifactId.servicios.EditorialServicio;
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
public class EditorialController {

    @Autowired
 	EditorialServicio editorialServicio;

    @Autowired
    EditorialRepositorio editorialRepositorio;

    @GetMapping("/listado-editoriales")
    public String editoriales(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);

        return "listado-editoriales.html";
    }

    @GetMapping("/crear-editorial")
    public String crearA() {

        return "crear-editorial.html";
    }

    @PostMapping("/crear-editorial")
    public String crear(@RequestParam String nombre) throws ErrorServicio {
        editorialServicio.crearEditorial1(nombre);

        return "redirect:/listado-editoriales";
    }

    @GetMapping("/modificar-editorial")
    public String modificar(){
        return "modificar-editorial.html";
    }
    
    @PostMapping("/modificar-editorial")
    public String modificarA(@RequestParam Long id, @RequestParam String nombre) throws ErrorServicio {
        editorialServicio.modificarEditorial(id, nombre);

        return "redirect:/listado-editoriales";
    }

}
