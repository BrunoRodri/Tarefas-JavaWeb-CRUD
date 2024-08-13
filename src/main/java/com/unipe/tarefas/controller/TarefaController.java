package com.unipe.tarefas.controller;

import com.unipe.tarefas.model.Tarefa;
import com.unipe.tarefas.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private final TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> listar() {
        return tarefaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> tarefaPorId(@PathVariable int id) {
        Optional<Tarefa> tarefa = tarefaService.findById(id);
        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Tarefa createTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.save(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> updateTarefa(@PathVariable Integer id, @RequestBody Tarefa tarefaDetails) {
        Optional<Tarefa> tarefa = tarefaService.findById(id);
        if (tarefa.isPresent()) {
            Tarefa tarefaToUpdate = tarefa.get();
            tarefaToUpdate.setNome(tarefaDetails.getNome());
            tarefaToUpdate.setStatus(tarefaDetails.getStatus());
            return ResponseEntity.ok(tarefaService.save(tarefaToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Integer id) {
        if (tarefaService.findById(id).isPresent()) {
            tarefaService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

