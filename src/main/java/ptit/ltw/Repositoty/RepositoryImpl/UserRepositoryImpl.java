package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Repositoty.UserRepository;
import ptit.ltw.Utils.HibernateUtils;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Log4j2
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public void save(@NotNull AppUser appUser) {
        Transaction tr = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.saveOrUpdate(appUser);
            tr.commit();
            session.close();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            log.error("Save user Exception: " + e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
    @Override
    public void delete(Long id) {
        Transaction tr = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            AppUser appUser = session.getReference(AppUser.class, id);
            tr = session.beginTransaction();
            session.delete(appUser);
            tr.commit();
            session.close();
        } catch (IllegalArgumentException | HibernateException e) {
            if(tr != null) tr.rollback();
            log.error("Delete user Exception: " + e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Collection<AppUser> getAll() {
        Collection<AppUser> users = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            session.beginTransaction();
            String HQL = "from AppUser";
            users = session.createQuery(HQL, AppUser.class).getResultList();
            session.getTransaction().commit();
            session.close();
        } catch (NoResultException | HibernateException e) {
            log.error("get all user Exception: " + e.getMessage());
        }
        return users;
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        Optional<AppUser> appUser = Optional.empty();
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            session.beginTransaction();
            appUser = session.byNaturalId(AppUser.class)
                    .using("email",email).loadOptional();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            log.error("find by email Exception: " + e.getMessage());
        }
        return appUser;
    }

    @Override
    public Optional<AppUser> findByPhone(String phone) {
        AppUser appUser = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
          session.beginTransaction();
            appUser = HibernateUtils
                    .getSessionFactory()
                    .openSession()
                    .createQuery("from AppUser where phone = ?1",AppUser.class)
                    .setParameter(1,phone).getSingleResult();
          session.getTransaction().commit();
          session.close();;
        }catch (NoResultException | HibernateException e) {
            e.getMessage();
        }
        return Optional.ofNullable(appUser);
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        Optional<AppUser> appUser = Optional.empty();
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            session.beginTransaction();
            appUser = session.byId(AppUser.class).loadOptional(id);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            log.error("find by id Exception: " + e.getMessage());
        }
        return appUser;

    }
}
