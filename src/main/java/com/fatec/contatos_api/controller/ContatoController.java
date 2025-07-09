package com.fatec.contatos_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.fatec.contatos_api.repository.ContatoRepository;
import com.fatec.contatos_api.entities.Contato;


@RestController
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "https://main.d35dmr9tr2ye12.amplifyapp.com")
public class ContatoController {

    @Autowired
    private ContatoRepository repository;

    @PostMapping
    public Contato criar(@RequestBody Contato contato) {
        return repository.save(contato);
    }

    @GetMapping
    public List<Contato> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para a pesquisa (Requisito 2)
    @GetMapping("/pesquisar")
    public List<Contato> pesquisarContatos(@RequestParam(required = false) String termo) {
        // Lógica simples de pesquisa (pode ser melhorada)
        return repository.findByNomeContainingIgnoreCase(termo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long id, @RequestBody Contato contatoDetalhes) {
        return repository.findById(id)
            .map(contato -> {
                contato.setNome(contatoDetalhes.getNome());
                contato.setEmail(contatoDetalhes.getEmail());
                contato.setTelefonePrimario(contatoDetalhes.getTelefonePrimario());
                contato.setTelefoneSecundario(contatoDetalhes.getTelefoneSecundario());
                contato.setEmpresa(contatoDetalhes.getEmpresa());
                contato.setCargo(contatoDetalhes.getCargo());
                contato.setAniversario(contatoDetalhes.getAniversario());
                contato.setCategoria(contatoDetalhes.getCategoria());
                contato.setFavorito(contatoDetalhes.isFavorito());
                // Salva o objeto 'contato' que acabamos de modificar
                Contato atualizado = repository.save(contato);
                return ResponseEntity.ok(atualizado);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
            .map(contato -> {
                repository.delete(contato);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para favoritar/desfavoritar
    @PatchMapping("/{id}/favorito")
    public ResponseEntity<Contato> favoritar(@PathVariable Long id) {
         return repository.findById(id)
            .map(contato -> {
                contato.setFavorito(!contato.isFavorito());
                repository.save(contato);
                return ResponseEntity.ok(contato);
            }).orElse(ResponseEntity.notFound().build());
    }
}