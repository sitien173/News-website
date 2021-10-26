package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Repositoty.IRepository.CrudCustomRepository;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class,isolation = Isolation.SERIALIZABLE,propagation = Propagation.REQUIRES_NEW)
@Slf4j
public class CrudCustomRepositoryImpl<T,ID> implements CrudCustomRepository<T,ID> {
    protected SessionFactory sessionFactory;
    protected Class<T> domainClass;

    public CrudCustomRepositoryImpl(SessionFactory sessionFactory, Class<T> domainClass) {
        this.sessionFactory = sessionFactory;
        this.domainClass = domainClass;
    }

    @Override
    public List<T> getAll() {
        String HQL = "From " + domainClass.getSimpleName();
        return sessionFactory.getCurrentSession().createQuery(HQL,domainClass).list();
    }

    @Override
    public void save(@NotNull T t) {
        Transaction transaction = null;
        try (Session session =  sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(t);
            transaction.commit();
        } catch (HibernateException e){
            log.error("Closing session after rollback error: ", e);
            if(transaction != null) transaction.rollback();
            throw new HibernateException("HibernateException: " + e.getCause().getMessage());
        }
    }

    @Override
    public void delete(ID id) throws ConstraintViolationException {
        Transaction transaction = null;
        try (Session session =  sessionFactory.openSession()){
            transaction = session.beginTransaction();
            T obj = session.getReference(domainClass,id);
            session.delete(obj);
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(transaction != null) transaction.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        return sessionFactory.getCurrentSession().byId(domainClass).loadOptional((Serializable) id);
    }

    @Override
    public Optional<T> findByNaturalId(Serializable value) {
        return sessionFactory.getCurrentSession().bySimpleNaturalId(domainClass).loadOptional(value);
    }
}
