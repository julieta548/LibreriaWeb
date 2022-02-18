

package libreriaWEB.artifactId.repositorios;

import libreriaWEB.artifactId.entidades.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long>{

}
