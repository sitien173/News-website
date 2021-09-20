package ptit.ltw.Repositoty.IRepository;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.Optional;

public interface CrudCustomRepository<T, ID>{
    void save(@NotNull T t);
    void delete(Class<T> className,ID id);
    Optional<T> findById(Class<T> className, ID id);
    Optional<T> findByNaturalId(Class<T> className, String fieldNameNaturalId, Serializable value);
    Collection<T> getAll(Class<T> className);
}