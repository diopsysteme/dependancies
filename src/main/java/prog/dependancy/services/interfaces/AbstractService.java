package prog.dependancy.services.interfaces;

import org.springframework.stereotype.Component;
import prog.dependancy.datas.entity.EntityAbstract;
import prog.dependancy.datas.repository.SoftDeleteRepository;
import prog.dependancy.web.mappper.GenericMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public abstract class AbstractService<T extends EntityAbstract, RQD,RPD> {

    protected SoftDeleteRepository<T, Long> repository;

    protected GenericMapper<T, RQD,RPD> mapper;
    public List<RPD> findAll() {        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<RPD> findById(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public RPD save(RQD dto) {
        T entity = mapper.toEntity(dto);
        T savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    public void deleteById(Long id) {
        repository.softDelete(id);
    }

    public RPD update(Long id,RQD dto) {
        T entity = mapper.toEntity(dto);
        T updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }
}
