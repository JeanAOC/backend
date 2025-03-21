package com.example.demo;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")  // Usa el perfil "test" (application-test.properties)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testSaveUsuario() {
        // Crear un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("password");

        // Guardar el usuario en la base de datos
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Verificar que el usuario se guardó correctamente
        assertNotNull(savedUsuario.getId());
        assertEquals("testuser", savedUsuario.getUsername());
        assertEquals("password", savedUsuario.getPassword());
    }

    @Test
    public void testFindByUsername() {
        // Crear un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("password");
        usuarioRepository.save(usuario);

        // Buscar el usuario por nombre de usuario
        Optional<Usuario> found = usuarioRepository.findByUsername("testuser");

        // Verificar que el usuario fue encontrado
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
    }
}