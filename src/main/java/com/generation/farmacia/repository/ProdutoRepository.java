package com.generation.farmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.farmacia.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

    
    public List<Produto> findAllByValor(@Param("valor") Long valor);

    public List<Produto> findAllByMarcaContainingIgnoreCase(@Param("marca") String marca);

    public List<Produto> findAllByTextoContainingIgnoreCase(@Param("texto") String texto);
    
    
    public List<Produto> findByValorBetween(@Param("valorMin") Long valorMin, @Param("valorMax") Long valorMax);
}
