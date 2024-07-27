package com.example.Spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Repository.ProdutoRepository;
import com.example.Spring.model.ProdutoModel;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> listarProdutos() {
        List<ProdutoModel> produtos = produtoRepository.findAll();
        return ResponseEntity.ok(produtos);
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    @PostMapping
    public ResponseEntity<ProdutoModel> criarProduto(@RequestBody ProdutoModel produto) {
        try {
            ProdutoModel novoProduto = produtoRepository.save(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null); // Retorne null ou um ProdutoModel padrão se necessário
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> buscarProdutoPorId(@PathVariable Long id) {
        Optional<ProdutoModel> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoModel produtoAtualizado) {
        Optional<ProdutoModel> optionalProduto = produtoRepository.findById(id);
        
        if (optionalProduto.isPresent()) {
            ProdutoModel produto = optionalProduto.get();
            produto.setNome(produtoAtualizado.getNome()); // Atualiza o nome
            produto.setPreco(produtoAtualizado.getPreco()); // Atualiza o preço
            // O usuarioId não é modificado aqui, mantendo o valor existente
            produtoRepository.save(produto);
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        Optional<ProdutoModel> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            produtoRepository.delete(produto.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
