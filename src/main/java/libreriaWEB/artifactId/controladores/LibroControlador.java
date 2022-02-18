package libreriaWEB.artifactId.controladores;

import java.util.Iterator;
import java.util.List;
import libreriaWEB.artifactId.entidades.Libro;
import libreriaWEB.artifactId.repositorios.AutorRepositorio;
import libreriaWEB.artifactId.servicios.ErrorServicio;
import libreriaWEB.artifactId.servicios.LibroServicio;
import libreriaWEB.artifactId.servicios.AutorServicio;
import libreriaWEB.artifactId.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LibroControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    LibroServicio libroServicio;

    @Autowired
    AutorServicio autorServicio;

    @Autowired
    AutorRepositorio autorRepositorio;

    //  @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    
    @GetMapping("/catalogo")
    public String listarCatalogoUsuario(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();

        modelo.addAttribute("libros", libros);

        return "catalogo.html";
    }

    
    @GetMapping("/catalogo-admin")
    public String listarCatalogoAdmin(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();

        modelo.addAttribute("libros", libros);
        return "catalogo-admin.html";
    }


    @PostMapping("/crear-libro")
    public String crear(@RequestParam String titulo, @RequestParam int anio, @RequestParam String apellidoAutor, @RequestParam String nombreAutor, @RequestParam String editorial, @RequestParam int ejemplares) throws ErrorServicio{
        libroServicio.crear(titulo, anio, ejemplares, nombreAutor, apellidoAutor, editorial);
        return "redirect:/catalogo-admin";
    }

    @GetMapping("/modificar-info")
    public String modificarInfoGet(){
        return "modificar-info.html";
    }

   @PostMapping("/modificar-info")
    public String modificarInfo(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam int anio, @RequestParam String apellidoAutor, @RequestParam String nombreAutor, @RequestParam String editorial, @RequestParam int ejemplares) throws ErrorServicio{
        libroServicio.modificarLibro(isbn, titulo, anio, ejemplares, nombreAutor, apellidoAutor, editorial);
        return "redirect:/catalogo-admin";
    }

    @GetMapping("/borrarLibro/{isbn}")
    public String borrarLibro(@PathVariable Long isbn) throws ErrorServicio {
        libroServicio.eliminar(isbn);

        return "redirect:/catalogo-admin";
    }


}
