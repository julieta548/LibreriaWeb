package libreriaWEB.artifactId.servicios;

import java.util.List;
import libreriaWEB.artifactId.entidades.Editorial;
import libreriaWEB.artifactId.entidades.Libro;
import libreriaWEB.artifactId.repositorios.EditorialRepositorio;
import libreriaWEB.artifactId.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public Editorial crearEditorial(Libro libro, String nombre) throws ErrorServicio {

        validacion2(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        editorial.getLibros().add(libro);
        editorialRepositorio.save(editorial);

        return editorial;
    }

      @Transactional
    public Editorial crearEditorial1(String nombre) throws ErrorServicio {

        validacion2(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);        
        editorialRepositorio.save(editorial);

        return editorial;
    }

    @Transactional
    public void modificarEditorial(Long id, String nombre) throws ErrorServicio {

        validacion3(id, nombre);
        editorialRepositorio.getById(id).setNombre(nombre);
        editorialRepositorio.save(editorialRepositorio.getById(id));
    }


    @Transactional
    public void alta(Long id) throws ErrorServicio {
        validacion4(id);
        editorialRepositorio.getById(id).setAlta(true);
        editorialRepositorio.save(editorialRepositorio.getById(id));
    }

    @Transactional
    public void baja(Long id) throws ErrorServicio {
        validacion4(id);
        editorialRepositorio.getById(id).setAlta(false);
        editorialRepositorio.save(editorialRepositorio.getById(id));
    }

    public List<Editorial> listarEditoriales() {
        return editorialRepositorio.findAll();
    }

    public List<Editorial> buscarXEditorial(String nombre) throws ErrorServicio {
        validacion(nombre);

        return editorialRepositorio.findByNombreIgnoreCase(nombre);
    }

    public void validacion(String nombre) throws ErrorServicio {

        if (nombre == null && nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }
    }

    public void validacion2(String nombre) throws ErrorServicio {
        if (nombre == null && nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }
    }

    public void validacion3(Long id, String nombre) throws ErrorServicio {
        if (id == null) {
            throw new ErrorServicio("El ID no puede estar vacío.");
        }

        if (nombre == null && nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }
    }

    public void validacion4(Long id) throws ErrorServicio {
        if (id == null) {
            throw new ErrorServicio("El ID no puede estar vacío.");
        }
    }
}
