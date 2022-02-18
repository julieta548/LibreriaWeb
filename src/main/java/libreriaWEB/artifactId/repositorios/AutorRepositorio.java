package libreriaWEB.artifactId.repositorios;

import java.util.List;
import libreriaWEB.artifactId.entidades.Autor;
import libreriaWEB.artifactId.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long>{

   public List<Libro> findDistinctAutorByApellidoOrNombre(String apellido, String nombre);
        
}
