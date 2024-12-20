package com.example.Spring.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.Spring.Repository.UsuarioRepository;
import com.example.Spring.model.UsuarioModel;

public class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioModel usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new UsuarioModel("João", "joao@example.com");
    }

    @Test
    public void testListarUsuarios() {
        List<UsuarioModel> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        ResponseEntity<List<UsuarioModel>> response = usuarioController.listarUsuarios();

        // Verificar se a lista não é nula antes de acessar o método size()
        List<UsuarioModel> body = response.getBody();
        if (body != null) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, body.size());
        } else {
            fail("A lista de usuários retornada é nula");
        }
    }

    @Test
    public void testCriarUsuario() {
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuario);

        ResponseEntity<UsuarioModel> response = usuarioController.criarUsuario(usuario);

        // Verificar se o corpo da resposta não é nulo
        UsuarioModel body = response.getBody();
        if (body != null) {
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(usuario, body);
        } else {
            fail("O corpo da resposta é nulo");
        }
    }

    @Test
    public void testBuscarUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<UsuarioModel> response = usuarioController.buscarUsuarioPorId(1L);

        // Verificar se o corpo da resposta não é nulo
        UsuarioModel body = response.getBody();
        if (body != null) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(usuario, body);
        } else {
            fail("O usuário não foi encontrado na resposta");
        }
    }

    @Test
public void testAtualizarUsuario() {
    UsuarioModel usuarioAtualizado = new UsuarioModel("João Atualizado", "joaoatualizado@example.com");
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
    when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioAtualizado);

    ResponseEntity<UsuarioModel> response = usuarioController.atualizarUsuario(1L, usuarioAtualizado);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    // Verificação de nulo para evitar NullPointerException
    UsuarioModel body = response.getBody();
    if (body != null) {
        assertEquals("João Atualizado", body.getNome());
        assertEquals("joaoatualizado@example.com", body.getEmail());
    } else {
        fail("O corpo da resposta de atualização de usuário é nulo");
    }
}

    @Test
    public void testDeletarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(any(UsuarioModel.class));

        ResponseEntity<Void> response = usuarioController.deletarUsuario(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeletarUsuarioNaoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = usuarioController.deletarUsuario(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
