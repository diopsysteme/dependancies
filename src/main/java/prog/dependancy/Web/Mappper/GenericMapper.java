package prog.dependancy.Web.Mappper;

import org.springframework.stereotype.Component;

public interface GenericMapper<E, RQD,RPD> {
    RPD toDto(E entity);
    E toEntity(RQD dto);
}
