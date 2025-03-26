package prog.dependancy.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prog.dependancy.datas.entity.EntityAbstract;
import prog.dependancy.services.interfaces.AbstractService;

import java.util.List;
public abstract class AbstractController<T extends EntityAbstract,  RQD,RPD> {

    protected final AbstractService<T,  RQD,RPD> service;

    public AbstractController(AbstractService<T,  RQD,RPD> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RPD>> findAll() {
        List<RPD> dtos = service.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RPD> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RPD> save(@RequestBody RQD dto) {
        RPD savedDto = service.save(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RPD> update(@PathVariable Long id, @RequestBody RQD dto) {
        RPD updatedDto = service.update(id, dto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}