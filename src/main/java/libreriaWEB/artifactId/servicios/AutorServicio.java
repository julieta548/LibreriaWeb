package libreriaWEB.artifactId.servicios;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import libreriaWEB.artifactId.entidades.Autor;
import libreriaWEB.artifactId.entidades.Libro;
import libreriaWEB.artifactId.repositorios.AutorRepositorio;
import libreriaWEB.artifactId.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private LibroServicio libroServicio;

    @Transactional
    public void crearAutor(String nombre, String apellido) throws ErrorServicio {

        validacion2(apellido, nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setApellido(apellido);
        autor.setAlta(true);
        autorRepositorio.save(autor);

    }

    @Transactional
    public Autor crearAutor1(Libro libro, String nombre, String apellido) throws ErrorServicio {

        validacion2(apellido, nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setApellido(apellido);
        autor.setAlta(true);
        autor.getLibros().add(libro);
        autorRepositorio.save(autor);
        return autor;
    }

    @Transactional
    public void modificarAutor(Long id, String nombre, String apellido) throws ErrorServicio {
        validacion3(id, nombre, apellido);
        autorRepositorio.getById(id).setNombre(nombre);
        autorRepositorio.getById(id).setApellido(apellido);

        autorRepositorio.save(autorRepositorio.getById(id));
    }

    @Transactional
    public void alta(Long id) throws ErrorServicio {
        validacion1(id);
        autorRepositorio.getById(id).setAlta(true);
        autorRepositorio.save(autorRepositorio.getById(id));
    }

    @Transactional
    public void baja(Long id) throws ErrorServicio {
        validacion1(id);
        autorRepositorio.getById(id).setAlta(false);
        autorRepositorio.save(autorRepositorio.getById(id));
    }

    public List<Autor> listarAutores() {
        return autorRepositorio.findAll();
    }

    @Transactional
    public void eliminar(Long id) throws ErrorServicio {
        validacion1(id);
        autorRepositorio.deleteById(id);
    }

    @Transactional
    public void anadirLibro(Long id, String titulo, int anio, int ejemplares, String nombreEditorial) throws ErrorServicio {
        Long isbn = libroServicio.crear(titulo, anio, ejemplares, autorRepositorio.getById(id).getNombre(), autorRepositorio.getById(id).getApellido(), nombreEditorial).getIsbn();
        autorRepositorio.getById(id).getLibros().add(libroRepositorio.getById(isbn));

    }

    public List<Libro> buscarXnombre(String apellido, String nombre) throws ErrorServicio {
        validacion(apellido, nombre);
        return autorRepositorio.findDistinctAutorByApellidoOrNombre(apellido, nombre);
    }

    public void validacion1(Long id) throws ErrorServicio {
        if (id == null) {
            throw new ErrorServicio("El ID no puede estar vacío.");
        }
    }

    public void validacion(String apellido, String nombre) throws ErrorServicio {
        if (apellido == null && apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacío.");
        }

        if (nombre == null && nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }

    }

    public void validacion2(String apellido, String nombre) throws ErrorServicio {
        if (apellido == null && apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacío.");
        }

        if (nombre == null && nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }
    }

    public void validacion3(Long id, String nombre, String apellido) throws ErrorServicio {
        if (id == null) {
            throw new ErrorServicio("El ID no puede estar vacío.");
        }

        if (nombre == null && nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }

        if (apellido == null && apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacío.");
        }

    }
}
