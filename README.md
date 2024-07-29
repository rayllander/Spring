# Tutorial:
![142-1425436_spring-boot](https://github.com/user-attachments/assets/3e0ff1a8-4401-4f14-9595-eb36c43199f9)

## Framework

Um framework é um conjunto de bibliotecas, ferramentas e padrões que fornecem uma estrutura para desenvolver e organizar software. Ele oferece um ambiente reutilizável e pré-definido que facilita o desenvolvimento de aplicações ao fornecer funcionalidades comuns, como gerenciamento de fluxo de dados, segurança, acesso a banco de dados, entre outros.

## O que é o Spring Boot?

O Spring Boot é um projeto do ecossistema Spring Framework que visa facilitar a criação de aplicativos Java autônomos e prontos para produção de forma rápida e com configuração mínima. Ele simplifica o desenvolvimento, oferecendo opiniões sobre bibliotecas e frameworks, reduzindo a quantidade de configuração que os desenvolvedores precisam escrever manualmente.

## O que é uma API?

Uma API (Interface de Programação de Aplicações) é um conjunto de regras e definições que permite que diferentes softwares se comuniquem entre si. Ela define como diferentes componentes de software devem interagir, especificando os métodos de comunicação e os dados que podem ser transferidos.

## 1-Conferir se tem Java instalado na máquina:

Execute o seguinte comando no terminal:

```bash
java -version
```

### 1.1-Para as maquinas que não tiverem o java instalado:

```bash
apt update && sudo apt install default-jre default-jdk
```

## 2-Conferir se o docker composer esta instalado na maquina:

```bash
docker-composer --version
```

### 2.1-Para as maquinas que não  estiverem com o composer intalado:

```bash
Sudo apt-get install composer
```

## 3-Abra o navegador e pesquise por:

```bash
Spring Initializr
```

### 3.1-O que é o Spring Initializr?


Spring Initializr é uma ferramenta web que facilita a criação inicial de projetos Spring Boot. Ele permite que os desenvolvedores configurem rapidamente um novo projeto Spring Boot com as dependências desejadas, evitando a necessidade de configuração manual extensa. Algumas das principais funcionalidades e características do Spring Initializr são: Seleção de dependências, Configuração do projeto, Geração de projeto, Integração com IDEs.

### 3.2-Print de como deve ser a configuração do projeto: 

![Captura de tela 2024-07-27 153022](https://github.com/user-attachments/assets/cf3922bd-0a62-4811-90e1-26f1fa497723)


#### Maven: Gerenciador de dependências ( baixa da internet todas as bibliotecas dependentes)

## Dependências: 

#### O Spring Data JPA 
é um projeto do Spring Framework que visa simplificar o acesso a dados e a implementação de repositórios utilizando o Java Persistence API (JPA). Ele fornece uma abstração de alto nível sobre o JPA, tornando o desenvolvimento de aplicações que interagem com bancos de dados mais eficiente e menos propenso a erros. 

#### O Spring Web

é um módulo do Spring Framework que fornece suporte para a criação de aplicações web, incluindo aplicações web tradicionais baseadas em servlets e aplicações web reativas. 

#### O MySQL Driver

 também conhecido como MySQL Connector/J, é um driver JDBC (Java Database Connectivity) para o banco de dados MySQL. Ele permite que aplicações Java se conectem e interajam com um banco de dados MySQL, executando operações como consultas, atualizações e outras transações SQL

 #### O Spring Security

 adiciona funcionalidades de segurança à sua aplicação Spring Boot. Ela fornece autenticação, autorização, e proteção contra ameaças comuns.

## 4-Criar o arquivo docker com as configurações do banco, baixe o arquivo:

docker-compose.yml

### 4.1-verifique se tem contêineres ativos na maquina: 
```bash
Listar: docker ps -a
Parar com nome ou id:  docker stop "nome do container"
remover com nome ou id:  docker rm "nome do container"
```
### 4.2-Para subir nosso container abra o terminal na pasta do arquivo “docker-compose.yml” e digite o comando:
```bash
docker-compose up -d
```
## 5-Criar a conexão com o banco de dados entre nas pastas: Spring/src/main/resoucers/ application.properties e copie o codigo do mesmo:
```bash
spring.datasource.url=jdbc:mariadb://localhost:3307/crud
spring.datasource.username=user1
spring.datasource.password=123
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect

logging.level.root=INFO
logging.level.org.springframework=DEBUG
logging.file.name=logs/application.log

spring.security.user.name=rayllander
spring.security.user.password=123

```
## 6-Organizar pastas para criação das classes, dentro do projeto Spring e dentro das pasta src/main/java/com/example/Spring crie as pastas:

Config

controller

Repository

model

## 7-Criar UsuarioModel.java:
```bash
package com.example.Spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    // Construtor padrão
    public UsuarioModel() {
    }

    // Construtor com parâmetros
    public UsuarioModel(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```
### 7.1-Criar ProdutoModel.java:
```bash
package com.example.Spring.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Produtos")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "usuario_id")
    private Long usuarioId;

    // Construtor padrão
    public ProdutoModel() {
    }

    // Construtor com parâmetros
    public ProdutoModel(String nome, BigDecimal preco, Long usuarioId) {
        this.nome = nome;
        this.preco = preco;
        this.usuarioId = usuarioId;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
```
## 8-Criar UsuarioController.java na pasta controller:
```bash
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

```
### 8.1-Criar ProdutoController.java na pasta controller:
```bash
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

```
## 9-Criar UsuarioRepository.java na pasta Repository:
```bash
package com.example.Spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Spring.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
}
```
### 9.1-Criar ProdutoRepository.java na pasta Repository:
```bash
package com.example.Spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Spring.model.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
}
```

## 10-Criar a autentificaçao SecurityConfig.java:
```bash
package com.example.Spring.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())  
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/produtos/**").permitAll() 
                                .requestMatchers("/api/usuarios/**").authenticated() 
   
                )
                .httpBasic(withDefaults());  // Habilitar autenticação básica

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("rayllander")
            .password("{noop}123")  // {noop} indica que a senha não está codificada
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}

```
## 11-Baixar Insomnia: 
```bash
verificar: insomnia –version
Baixar: https://insomnia.rest/download
```
## 12-Testes no Insomnia:
#### Adicionar:
POST http://localhost:8080/api/usuarios

Corpo:
```bash
{
  "nome": "usuario",
  "email": "usuarioExemplo@gmail.com"
}
```

Headers: Content-Type     application/json

  username:
  
  password:
  
#### Listar: 
GET http://localhost:8080/api/usuarios
Auth
  username:
  password:
  
#### Adicionar: 
POST http://localhost:8080/api/produtos

Corpo:
```bash
{
  "nome": "melancia",
    "preco": 20.0,
    "usuarioId": 1
}

```
Headers: Content-Type     application/json

  username:
  
  password:
  
#### Editar
PUT http://localhost:8080/api/produtos/1
Corpo:
```bash
{	
	"nome": "abacaxi",
	"preco": 15.00,
	"usuarioId": 1
}
```

Headers: Content-Type     application/json

  username:
  
  password:
  
 #### Buscar por id
GET http://localhost:8080/api/produtos/1

  username:
  
  password:
  
#### Excluir
DELETE http://localhost:8080/api/produtos/1

  username:
  
  password:








 

























