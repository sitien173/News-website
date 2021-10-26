package ptit.ltw.Repositoty.IRepository;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface CrudCustomRepository<T, ID>{
    void save(@NotNull T t);
    void delete(ID id);
    Optional<T> findById(ID id);
    Optional<T> findByNaturalId(Serializable value);
    Collection<T> getAll();
}