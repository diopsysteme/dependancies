package prog.dependancy.datas.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface SoftDeleteRepository<T, ID> extends JpaRepository<T, ID> {

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deleted = true, e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = ?1")
    void softDelete(Long id);

    @Override
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
    List<T> findAll();
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = true")
    List<T> findAllByDeletedTrue();
    @Query("SELECT e FROM #{#entityName} e")
    List<T> findAllConfondu();
}
