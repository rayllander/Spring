//mvn -Dtest=ProdutoControllerTest test


package com.example.Spring.controller;

import java.math.BigDecimal;
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

import com.example.Spring.Repository.ProdutoRepository;
import com.example.Spring.model.ProdutoModel;

public class ProdutoControllerTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoController produtoController;

    private ProdutoModel produto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Use BigDecimal para o preço e Long para o id
        produto = new ProdutoModel("Produto 1", new BigDecimal("100.0"), 1L);
    }

    @Test
    public void testListarProdutos() {
        List<ProdutoModel> produtos = Arrays.asList(produto);
        when(produtoRepository.findAll()).thenReturn(produtos);

        ResponseEntity<List<ProdutoModel>> response = produtoController.listarProdutos();

        List<ProdutoModel> body = response.getBody();
        if (body != null) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, body.size());
        } else {
            fail("A lista de produtos retornada é nula");
        }
    }

    @Test
    public void testCriarProduto() {
        when(produtoRepository.save(any(ProdutoModel.class))).thenReturn(produto);

        ResponseEntity<ProdutoModel> response = produtoController.criarProduto(produto);

        ProdutoModel body = response.getBody();
        if (body != null) {
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(produto, body);
        } else {
            fail("O corpo da resposta é nulo");
        }
    }

    @Test
    public void testBuscarProdutoPorId() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ResponseEntity<ProdutoModel> response = produtoController.buscarProdutoPorId(1L);

        ProdutoModel body = response.getBody();
        if (body != null) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(produto, body);
        } else {
            fail("Produto não encontrado");
        }
    }

    @Test
    public void testAtualizarProduto() {
        ProdutoModel produtoAtualizado = new ProdutoModel("Produto Atualizado", new BigDecimal("120.0"), 1L); 
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(ProdutoModel.class))).thenReturn(produtoAtualizado);

        ResponseEntity<ProdutoModel> response = produtoController.atualizarProduto(1L, produtoAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ProdutoModel body = response.getBody();
        if (body != null) {
            assertEquals("Produto Atualizado", body.getNome());
            assertEquals(new BigDecimal("120.0"), body.getPreco());
        } else {
            fail("O corpo da resposta de atualização de produto é nulo");
        }
    }

    @Test
    public void testDeletarProduto() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).delete(any(ProdutoModel.class));

        ResponseEntity<Void> response = produtoController.deletarProduto(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeletarProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = produtoController.deletarProduto(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
