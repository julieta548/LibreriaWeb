package libreriaWEB.artifactId.servicios;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import libreriaWEB.artifactId.entidades.Prestamo;
import libreriaWEB.artifactId.repositorios.LibroRepositorio;
import libreriaWEB.artifactId.repositorios.PrestamoRepositorio;
import libreriaWEB.artifactId.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Prestamo crearPrestamo(Long idUsuario, Long isbn, Date fechaPrestamo, Date fechaDevolucion) throws ErrorServicio {

        Prestamo prestamo = new Prestamo();
        
        validacion(idUsuario, isbn, fechaPrestamo, fechaDevolucion);

        prestamo.setAlta(true);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setUsuario(usuarioRepositorio.getById(idUsuario));
        prestamo.getLibros().add(libroRepositorio.getById(isbn));

        usuarioRepositorio.getById(idUsuario).getPrestamos().add(prestamo);
        usuarioRepositorio.save(usuarioRepositorio.getById(idUsuario));
        
        return prestamoRepositorio.save(prestamo);
    }

    public void modificarPrestamo(Long id, Long idUsuario, Long isbn, Date fechaPrestamo, Date fechaDevolucion) throws ErrorServicio {
        validacion(idUsuario, isbn, fechaPrestamo, fechaDevolucion);

        prestamoRepositorio.getById(id).setAlta(true);
        prestamoRepositorio.getById(id).setFechaPrestamo(fechaPrestamo);
        prestamoRepositorio.getById(id).setFechaDevolucion(fechaDevolucion);
        prestamoRepositorio.getById(id).setUsuario(usuarioRepositorio.getById(id));
        prestamoRepositorio.getById(id).getLibros().add(libroRepositorio.getById(isbn));

        prestamoRepositorio.save(prestamoRepositorio.getById(id));

        libroRepositorio.getById(isbn).setAlta(false);

        libroRepositorio.save(libroRepositorio.getById(isbn));

    }

    public void bajarPrestamo(Long id, Long isbn) throws ErrorServicio {

        validacion1(id, isbn);
        
        prestamoRepositorio.getById(id).setAlta(false);
        prestamoRepositorio.save(prestamoRepositorio.getById(id));

        libroRepositorio.getById(isbn).setAlta(true);
        libroRepositorio.save(libroRepositorio.getById(isbn));

    }

    public void validacion(Long idUsuario, Long isbn, Date fechaPrestamo, Date fechaDevolucion) throws ErrorServicio {
        if (idUsuario == null) {
            throw new ErrorServicio("Usuario vacío.");
        }

        if (isbn == null) {
            throw new ErrorServicio("El isbn no puede estar vacío.");
        }

        if (fechaPrestamo == null) {
            throw new ErrorServicio("La fecha inicio del préstamo no puede estar vacía.");
        }
        if (fechaDevolucion == null) {
            throw new ErrorServicio("La fecha final del préstamo no puede estar vacía.");
        }

    }
    
    public void validacion1(Long id, Long isbn) throws ErrorServicio{
        if (id == null) {
            throw new ErrorServicio("ID del prestamo vacío.");
            
        }
        if (isbn == null) {
            throw new ErrorServicio("ISBN vacío.");
            
        }
    }
}
