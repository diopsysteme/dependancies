package prog.dependancy.web.mappper;

public interface GenericMapper<E, RQD,RPD> {
    RPD toDto(E entity);
    E toEntity(RQD dto);
}
