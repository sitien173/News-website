package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Category;
import ptit.ltw.Repositoty.IRepository.CrudCustomRepository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.lang.InstantiationException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
@Transactional
@Repository
@AllArgsConstructor
@Slf4j
public class CrudCustomRepositoryImpl<T,ID> implements CrudCustomRepository<T,ID> {
    protected final SessionFactory sessionFactory;
    @Override
    public List<T> getAll(Class<T> className) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String HQL = "From " + className.getSimpleName();
            return session.createQuery(HQL,className).list();
        }catch (NoResultException e){
           // true logic
        } catch (HibernateException e) {
            throw new HibernateException("HibernateException: " + e.getMessage());
        }
        return null;
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
    public void delete(Class<T> className,ID id) throws ConstraintViolationException {
        Transaction transaction = null;
        try (Session session =  sessionFactory.openSession();){
            transaction = session.beginTransaction();
            T obj = session.getReference(className,id);
            session.delete(obj);
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(transaction != null) transaction.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        }
    }

    @Override
    public Optional<T> findById(Class<T> className, ID id) {
        Optional<T> optional;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            optional = session.byId(className).loadOptional((Serializable) id);
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(transaction != null) transaction.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        }
        return optional;
    }

    @Override
    public Optional<T> findByNaturalId(Class<T> className,String fieldNameNaturalId,Serializable value) {
        Optional<T> optional;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            optional = session.byNaturalId(className)
                    .using(fieldNameNaturalId,value).loadOptional();
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(transaction != null) transaction.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        }
        return optional;
    }

}
