package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.AllArgsConstructor;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Category;
import ptit.ltw.Repositoty.IRepository.CrudCustomRepository;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.InstantiationException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CrudCustomRepositoryImpl<T,ID> implements CrudCustomRepository<T,ID> {
    protected final SessionFactory sessionFactory;

    @Override
    public List<T> getAll(Class<T> className) {
        Session session = null;
        List<T> list = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            String HQL = "From " + className.getSimpleName();
            list = session.createQuery(HQL,className)
                    .getResultList();
            session.getTransaction().commit();
        }catch (NoResultException e){
            e.getMessage();
        } catch (HibernateException e) {
            e.printStackTrace();
        }  finally {
            if(session != null) session.close();
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
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
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
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } catch (IllegalAccessException | InstantiationException  e) {
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
    }

    @Override
    public Optional<T> findById(Class<T> className, ID id) {
        Session session =  null;
        Optional<T> optional = Optional.empty();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            optional = session.byId(className).loadOptional((Serializable) id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
        return optional;
    }

    @Override
    public Optional<T> findByNaturalId(Class<T> className,String fieldNameNaturalId,Serializable value) {
        Session session =  null;
        Optional<T> optional = Optional.empty();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            optional = session.byNaturalId(className)
                    .using(fieldNameNaturalId,value).loadOptional();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
        return optional;
    }

}