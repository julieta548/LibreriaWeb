package libreriaWEB.artifactId.controladores;

import java.util.Iterator;
import java.util.List;
import libreriaWEB.artifactId.entidades.Libro;
import libreriaWEB.artifactId.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import libreriaWEB.artifactId.servicios.UsuarioServicio;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private LibroServicio libroServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/listarU")
    public String listarU(ModelMap modelo) {
        String boton;
        List<Libro> libros = libroServicio.listarLibros();

        modelo.addAttribute("libros", libros);

        for (Iterator<Libro> iterator = libros.iterator(); iterator.hasNext();) {
            Libro next = iterator.next();
            if (next.isAlta()) {
                boton = "Lend";
                modelo.addAttribute("boton", boton);

            } else {
                boton = "Reserve";
                modelo.addAttribute("boton", boton);
            }
        }

        return "listado-de-libros.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/prestamos")
    public String prestamos() {
        return "listado-prestamos.html";
    }

}
