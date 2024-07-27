package com.example.Spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Repository.UsuarioRepository;
import com.example.Spring.model.UsuarioModel;

@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        try {
            List<UsuarioModel> usuarios = usuarioRepository.findAll();
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioModel> criarUsuario(@RequestBody UsuarioModel usuario) {
        try {
            // Verifica se os dados do usuário não são nulos
            if (usuario.getNome() == null || usuario.getEmail() == null) {
                return ResponseEntity.badRequest().build();
            }
    
            UsuarioModel novoUsuario = usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioModel> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuarioAtualizado) {
        try {
            Optional<UsuarioModel> optionalUsuario = usuarioRepository.findById(id);
            if (optionalUsuario.isPresent()) {
                UsuarioModel usuario = optionalUsuario.get();
                usuario.setNome(usuarioAtualizado.getNome());
                usuario.setEmail(usuarioAtualizado.getEmail());
                usuarioRepository.save(usuario);
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////
   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
            if (usuario.isPresent()) {
                usuarioRepository.delete(usuario.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
