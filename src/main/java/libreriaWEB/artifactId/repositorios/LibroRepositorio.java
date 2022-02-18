package libreriaWEB.artifactId.repositorios;

import libreriaWEB.artifactId.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

    public Libro findByTituloIgnoreCase(String titulo);
}
