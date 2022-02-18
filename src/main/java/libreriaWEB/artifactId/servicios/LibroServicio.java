package libreriaWEB.artifactId.servicios;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import libreriaWEB.artifactId.entidades.Libro;
import libreriaWEB.artifactId.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    List<Libro> libros = new ArrayList<>();

    @Transactional
    public Libro crear(String titulo, int anio, int ejemplares, String nombreAutor, String apellidoAutor, String nombreEditorial) throws ErrorServicio {

        validacion(titulo, anio, ejemplares, nombreAutor, apellidoAutor, nombreEditorial);

        Libro libro = new Libro();

        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setAlta(true);
        libro.setAutor(autorServicio.crearAutor1(libro, nombreAutor, apellidoAutor));
        libro.setEditorial(editorialServicio.crearEditorial(libro, nombreEditorial));

        return libroRepositorio.save(libro);
    }

    @Transactional
    public Libro modificarLibro(Long isbn, String titulo, int anio, int ejemplares, String nombreAutor, String apellidoAutor, String nombreEditorial) throws ErrorServicio {

        validacion2(isbn, titulo, anio, ejemplares, titulo, titulo, titulo);

        Libro libro = libroRepositorio.getById(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAlta(true);
        libro.setAutor(autorServicio.crearAutor1(libro, nombreAutor, apellidoAutor));
        libro.setEditorial(editorialServicio.crearEditorial(libro, nombreEditorial));
        return libroRepositorio.save(libro);

    }

    @Transactional
    public void alta(Long isbn) throws ErrorServicio {
        validacion3(isbn);
        Libro libro = libroRepositorio.getById(isbn);
        libro.setAlta(true);
        libroRepositorio.save(libro);
    }

    @Transactional
    public void baja(Long isbn) throws ErrorServicio {
        validacion3(isbn);
        Libro libro = libroRepositorio.getById(isbn);
        libro.setAlta(false);
        libroRepositorio.save(libro);
    }

    public void eliminar(Long isbn) throws ErrorServicio {
        validacion3(isbn);
        libroRepositorio.deleteById(isbn);
    }

    public Libro buscarXisbn(Long isbn) throws ErrorServicio {
        validacion3(isbn);
        return libroRepositorio.getById(isbn);
    }

    public List<Libro> listarLibros() {
        return em.createQuery("SELECT l FROM Libro l").getResultList();
    }

    public Libro buscarXtitulo(String titulo) throws ErrorServicio {
        validacion4(titulo);
        return libroRepositorio.findByTituloIgnoreCase(titulo);
    }

    public void validacion(String titulo, int anio, int ejemplares, String nombreAutor, String apellidoAutor, String nombreEditorial) throws ErrorServicio {

        if (titulo == null && titulo.isEmpty()) {
            throw new ErrorServicio("El título no puede estar vacío.");
        }
        if (nombreAutor == null && nombreAutor.isEmpty()) {
            throw new ErrorServicio("El nombre del/de la autor/a no puede estar vacío.");
        }
        if (nombreEditorial == null && nombreEditorial.isEmpty()) {
            throw new ErrorServicio("El nombre de la editorial no puede estar vacío.");
        }
        if (apellidoAutor == null && apellidoAutor.isEmpty()) {
            throw new ErrorServicio("El apellido del autor no puede estar vacío.");
        }
        if (ejemplares == 0) {
            throw new ErrorServicio("El número de ejemplares no puede estar vacío ni ser cero.");
        }
        if (anio == 0) {
            throw new ErrorServicio("El año no puede estar vacío ni ser cero.");
        }

    }

    public void validacion2(Long isbn, String titulo, int anio, int ejemplares, String nombreAutor, String apellidoAutor, String nombreEditorial) throws ErrorServicio {

        if (titulo == null && titulo.isEmpty()) {
            throw new ErrorServicio("El título no puede estar vacío.");
        }
        if (nombreAutor == null && nombreAutor.isEmpty()) {
            throw new ErrorServicio("El nombre del/de la autor/a no puede estar vacío.");
        }
        if (nombreEditorial == null && nombreEditorial.isEmpty()) {
            throw new ErrorServicio("El nombre de la editorial no puede estar vacío.");
        }
        if (apellidoAutor == null && apellidoAutor.isEmpty()) {
            throw new ErrorServicio("El apellido del autor no puede estar vacío.");
        }
        if (ejemplares == 0) {
            throw new ErrorServicio("El número de ejemplares no puede estar vacío ni ser cero.");
        }
        if (anio == 0) {
            throw new ErrorServicio("El año no puede estar vacío ni ser cero.");
        }
        if (isbn == null) {
            throw new ErrorServicio("El ISBN no puede estar vacío ni ser cero.");
        }

    }

    public void validacion3(Long isbn) throws ErrorServicio {
        if (isbn == null) {
            throw new ErrorServicio("El ISBN no puede estar vacío.");
        }
    }

    public void validacion4(String titulo) throws ErrorServicio {
        if (titulo == null && titulo.isEmpty()) {
            throw new ErrorServicio("El título no puede estar vacío.");
        }
    }
}
