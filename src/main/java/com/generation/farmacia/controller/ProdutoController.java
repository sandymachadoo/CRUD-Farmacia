package com.generation.farmacia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    public ProdutoRepository produtoRepository;

    @Autowired
    public CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
    }

   
    @GetMapping("/valor")
    public ResponseEntity<List<Produto>> getByValor(@RequestParam Long valor) {
        return ResponseEntity.ok(produtoRepository.findAllByValor(valor)); 
    }

   
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Produto>> getByMarca(@PathVariable String marca) {
        return ResponseEntity.ok(produtoRepository.findAllByMarcaContainingIgnoreCase(marca));
    }

    
    @GetMapping("/texto/{texto}")
    public ResponseEntity<List<Produto>> getByTexto(@PathVariable String texto) {
        return ResponseEntity.ok(produtoRepository.findAllByTextoContainingIgnoreCase(texto));
    }

   
    @PostMapping
    public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
        
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(produto.getCategoria().getId());
        
        if (categoriaOptional.isPresent()) {
           
            produto.setCategoria(categoriaOptional.get());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(produtoRepository.save(produto));
        }
        
       
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada", null);
    
    }

   
    @PutMapping
    public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
        return produtoRepository.findById(produto.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(produtoRepository.save(produto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        produtoRepository.deleteById(id);
    }
}
