package ptit.ltw.Repositoty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface CrudCustomRepository<T, ID>{
    void save(@NotNull T t);
    void delete(ID id);
    Optional<T> findById(Class<T> className, Serializable id);
    Optional<T> findByNaturalId(Class<T> className, String fieldNameNaturalId, Serializable value);
    Collection<T> getAll(Class<T> className);
}