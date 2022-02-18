

package libreriaWEB.artifactId.repositorios;

import libreriaWEB.artifactId.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

    @Query(value = "SELECT u FROM Usuario u WHERE u.nombre LIKE :nombre")
    public Usuario findByNombre(@Param ("nombre") String nombre);
    
}
