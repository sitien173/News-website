package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Category;
import ptit.ltw.Repositoty.IRepository.CrudCustomRepository;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.lang.InstantiationException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class CrudCustomRepositoryImpl<T,ID> implements CrudCustomRepository<T,ID> {
    protected final SessionFactory sessionFactory;

    @Override
    public List<T> getAll(Class<T> className) {
        List<T> list = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            String HQL = "From " + className.getSimpleName();
            list = session.createQuery(HQL,className)
                    .getResultList();
        }catch (NoResultException e){
           // true logic
        } catch (HibernateException e) {
            throw new HibernateException("HibernateException: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void save(@NotNull T t) {
        Transaction transaction = null;
        try (Session session =  sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(t);
            transaction.commit();
        } catch (DataException e1){
            log.error("Closing session after rollback error: ", e1);
            if(transaction != null) transaction.rollback();
            throw new DataException(e1.getCause().getMessage(),e1.getSQLException());
        } catch (GenericJDBCException e2){
            log.error("Closing session after rollback error: ", e2);
            if(transaction != null) transaction.rollback();
            throw new GenericJDBCException(e2.getCause().getMessage(),e2.getSQLException()  );
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
