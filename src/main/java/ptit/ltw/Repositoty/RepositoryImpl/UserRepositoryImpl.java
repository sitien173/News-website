package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Repositoty.UserRepository;
import ptit.ltw.Utils.HibernateUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Log4j2
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;
    @Override
    public void save(@NotNull AppUser appUser){
       Session session =  null;
       Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            session.saveOrUpdate(appUser);
            tr.commit();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();

        }
    }
    @Override
    public void delete(Long id) {
        Session session =  null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            AppUser appUser = session.getReference(AppUser.class, id);
            tr = session.beginTransaction();
            session.delete(appUser);
            tr.commit();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
    }

    @Override
    public Collection<AppUser> getAll(){
        Session session = null;
        Transaction tr = null;
        Collection<AppUser> appUser = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            appUser = session.createQuery("from AppUser",AppUser.class).getResultList();
            tr.commit();
        } catch (NoResultException e){
            // true
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
      return appUser;
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        Session session =  null;
        Transaction tr = null;
        Optional<AppUser> appUser = Optional.empty();
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            appUser = session.byNaturalId(AppUser.class)
                                 .using("email",email)
                                    .loadOptional();
            tr.commit();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
       return appUser;
    }
    @Override
    public Optional<AppUser> findById(Long id){
        Session session =  null;
        Transaction tr = null;
        Optional<AppUser> appUser = Optional.empty();
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            appUser = session.byId(AppUser.class).loadOptional(id);
            tr.commit();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
       return appUser;
    }
}
