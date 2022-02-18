package libreriaWEB.artifactId.repositorios;

import java.util.List;
import libreriaWEB.artifactId.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, Long> {

    public List<Editorial> findByNombreIgnoreCase(String nombre);
}
