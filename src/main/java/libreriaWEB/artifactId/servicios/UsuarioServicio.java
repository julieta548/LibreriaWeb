package libreriaWEB.artifactId.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import libreriaWEB.artifactId.entidades.Usuario;
import libreriaWEB.artifactId.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearUsuario(String nombre, String clave) throws ErrorServicio {

        validacion1(nombre, clave);
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setAlta(true);
        
        String encriptado = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptado);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void modificarUsuario(Long id, String nombre, String clave) throws ErrorServicio {
        validacion1(nombre, clave);

        Optional<Usuario> usuario = usuarioRepositorio.findById(id);
        Usuario usuarioRespuesta = usuario.get();
        usuarioRespuesta.setNombre(nombre);

        String encriptado = new BCryptPasswordEncoder().encode(clave);
        usuarioRespuesta.setClave(encriptado);

        usuarioRepositorio.save(usuarioRespuesta);
    }

    @Transactional
    public void eliminar(String clave, Long id) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.getById(id);
        if (clave.equals(usuario.getClave())) {
            usuarioRepositorio.delete(usuario);
        } else {
            throw new ErrorServicio("Clave incorrecta");
        }

    }

    @Transactional
    public void alta(String clave, Long id) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.getById(id);
        if (clave.equals(usuario.getClave())) {
            usuarioRepositorio.getById(id).setAlta(true);
        } else {
            throw new ErrorServicio("Clave incorrecta");
        }

    }

    @Transactional
    public void baja(Long id) {
        usuarioRepositorio.getById(id).setAlta(false);
    }

    public Usuario buscarPorId(Long id) throws ErrorServicio {

        Usuario usuario = usuarioRepositorio.getById(id);

        if (usuario != null) {

            return usuario;
        } else {
            throw new ErrorServicio("No se encontro el usuario buscado.");
        }
    }

    public void validacion(String nombre, String apellido, String domicilio, String dni) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacío.");
        }

        if (domicilio == null || domicilio.isEmpty()) {
            throw new ErrorServicio("El domicilio no puede estar vacío.");
        }

        if (dni == null || dni.isEmpty()) {
            throw new ErrorServicio("El dni no puede estar vacío ni ser 0.");
        }
    }

    public void validacion1(String nombre, String clave) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }

        
        if (clave == null || clave.isEmpty()) {
            throw new ErrorServicio("La clave no puede estar vacía ni ser nula.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByNombre(nombre);
        if (usuario != null) {

            //esta lista contiene el listado de permisos del cliente
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            List<GrantedAuthority> permisosAdmin = new ArrayList<>();

            //creo los permisos
            //con este rol va a poder ingresar a algunos metodos
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
            
            GrantedAuthority p2 = new SimpleGrantedAuthority("ROLE_ADMIN_REGISTRADO");
            permisosAdmin.add(p1);
            
            //recupero el usuario que inicio sesion
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            //transformo al cliente en un cliente del dominio de Spring
            //nos pide usuario,clave y permisos
            User user = new User(usuario.getNombre(), usuario.getClave(), permisos);
            return user;
        } else {
            return null;
        }
    }
}
