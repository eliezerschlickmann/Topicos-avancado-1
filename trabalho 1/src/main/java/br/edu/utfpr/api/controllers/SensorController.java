package br.edu.utfpr.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import br.edu.utfpr.api.dto.SensorDTO;
import br.edu.utfpr.api.exceptions.NoteFoundException;
import br.edu.utfpr.api.model.Sensor;
import br.edu.utfpr.api.service.SensorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    @Autowired
        private SensorService sensorService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id){
        var person = sensorService.getByid(id);
    
        return person.isPresent() ? ResponseEntity.ok().body(person.get())
        : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Sensor> getAll(){
        return sensorService.getAll();
    }

    @PostMapping    
    public ResponseEntity<Object> create(@RequestBody SensorDTO dto){
        try{
            var res = sensorService.create(dto);

            //seta o status para 201 (CREATED)  e retorna o objeto Sensor em JSON 
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }catch(Exception ex){
            // seta o status para 400 (bad request) e devolve a mensagem da exceção lançada.
            return ResponseEntity.badRequest().body(ex.getMessage());       
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody SensorDTO dto){
        try {
            return ResponseEntity.ok().body(sensorService.update(id, dto));
        } catch (NoteFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch(Exception ex){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        try {
            sensorService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NoteFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
