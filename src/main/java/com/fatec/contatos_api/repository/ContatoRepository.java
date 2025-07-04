package com.fatec.contatos_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fatec.contatos_api.entities.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByNomeContainingIgnoreCase(String nome);
    List<Contato> findByEmailContainingIgnoreCase(String email);
    List<Contato> findByTelefonePrimarioContaining(String telefone);
}
