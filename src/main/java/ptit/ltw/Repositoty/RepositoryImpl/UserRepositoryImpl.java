package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(appUser);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Save user Exception: " + e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull AppUser appUser) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(appUser);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Update user Exception: " + e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            AppUser appUser = session.get(AppUser.class,id);
            session.beginTransaction();
            session.delete(appUser);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Delete user Exception: " + e.getMessage());
           throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Collection<? extends AppUser> getAll() {
        Collection<? extends AppUser> users = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "select u from  AppUser u";
            users = session.createQuery(HQL, AppUser.class).getResultList();
            session.getTransaction().commit();
        } catch (NoResultException | HibernateException e) {
            log.error("get all user Exception: " + e.getMessage());
        }
        return users;
    }

    @Override
    public Optional<? extends AppUser> findByEmail(String email) {
        AppUser appUser = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "from AppUser where email = :email";
            appUser = session.createQuery(HQL, AppUser.class).setParameter("email", email).getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException | HibernateException e) {
           log.error("Find By Email Exception: " + e.getMessage());
        }
        return Optional.ofNullable(appUser);
    }

    @Override
    public Optional<? extends AppUser> findByPhone(String phone) {
        AppUser appUser = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "from AppUser where phone = :phone";
            appUser = session.createQuery(HQL, AppUser.class).setParameter("phone", phone).getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException | HibernateException e) {
            log.error("Find By Phone Exception: " + e.getMessage());
        }
        return Optional.ofNullable(appUser);
    }

    @Override
    public Optional<? extends AppUser> findById(Long id) {
        AppUser appUser = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            appUser =  session.get(AppUser.class,id);
        } catch (NoResultException | HibernateException e) {
            log.error("Find By Id Exception: " + e.getMessage());
        }
        return Optional.ofNullable(appUser);
    }
}
