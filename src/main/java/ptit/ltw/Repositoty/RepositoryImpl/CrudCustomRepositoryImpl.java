package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Category;
import ptit.ltw.Repositoty.IRepository.CrudCustomRepository;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.InstantiationException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class CrudCustomRepositoryImpl<T,ID> implements CrudCustomRepository<T,ID> {
    protected final SessionFactory sessionFactory;

    @Override
    public List<T> getAll(Class<T> className) {
        Session session = null;
        Transaction tr = null;
        List<T> list = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            String HQL = "From " + className.getSimpleName();
            list = session.createQuery(HQL,className)
                    .getResultList();
            tr.commit();
        }catch (NoResultException e){
           // true logic
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(tr != null && tr.isActive()) tr.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        }  finally {
            if(session != null && session.isOpen()) session.close();
        }
        return list;
    }

    @Override
    public void save(@NotNull T t) {
        Session session =  null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            session.saveOrUpdate(t);
            tr.commit();
        } catch (DataException e1){
            log.error("Closing session after rollback error: ", e1);
            if(tr != null && tr.isActive()) tr.rollback();
            throw new DataException(e1.getCause().getMessage(),e1.getSQLException());
        } catch (HibernateException e){
            log.error("Closing session after rollback error: ", e);
            if(tr != null && tr.isActive()) tr.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        } finally {
            if(session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void delete(Class<T> className,ID id) throws ConstraintViolationException {
        Session session =  null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            T obj = className.newInstance();
            tr = session.beginTransaction();
            if(obj instanceof AppUser)
                ((AppUser) obj).setId((Long)id);
            else if(obj instanceof Category)
                ((Category) obj).setId((Integer) id);
            session.delete(obj);
            tr.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(tr != null && tr.isActive()) tr.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        } catch (IllegalAccessException | InstantiationException  e) {
           log.error(e.getMessage());
        } finally {
            if(session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Optional<T> findById(Class<T> className, ID id) {
        Session session =  null;
        Transaction tr = null;
        Optional<T> optional = Optional.empty();
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            optional = session.byId(className).loadOptional((Serializable) id);
            tr.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(tr != null && tr.isActive()) tr.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        } finally {
            if(session != null && session.isOpen()) session.close();
        }
        return optional;
    }

    @Override
    public Optional<T> findByNaturalId(Class<T> className,String fieldNameNaturalId,Serializable value) {
        Session session =  null;
        Transaction tr = null;
        Optional<T> optional = Optional.empty();
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            optional = session.byNaturalId(className)
                    .using(fieldNameNaturalId,value).loadOptional();
            tr.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(tr != null && tr.isActive()) tr.rollback();
            throw new HibernateException("HibernateException: " + e.getMessage());
        } finally {
            if(session != null && session.isOpen()) session.close();
        }
        return optional;
    }

}
