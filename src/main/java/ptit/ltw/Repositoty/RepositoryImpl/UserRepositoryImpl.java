package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.User;
import ptit.ltw.Repositoty.UserRepository;
import ptit.ltw.Utils.HibernateUtils;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Log4j2
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public void save(@NotNull User user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Save user Exception: " + e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull User user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Update user Exception: " + e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class,id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Delete user Exception: " + e.getMessage());
           throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Collection<? extends User> getAll() {
        Collection<? extends User> users = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "select u from  User u";
            users = session.createQuery(HQL,User.class).getResultList();
            session.getTransaction().commit();
        } catch (NoResultException | HibernateException e) {
            log.error("get all user Exception: " + e.getMessage());
        }
        return users;
    }

    @Override
    public Optional<? extends User> findByEmail(String email) {
        User user = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "from User where email = :email";
            user = session.createQuery(HQL, User.class).setParameter("email", email).getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException | HibernateException e) {
           log.error("Find By Email Exception: " + e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<? extends User> findByPhone(String phone) {
        User user = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "from User where phone = :phone";
            user = session.createQuery(HQL, User.class).setParameter("phone", phone).getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException | HibernateException e) {
            log.error("Find By Phone Exception: " + e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<? extends User> findById(Long id) {
        User user = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            user =  session.get(User.class,id);
            session.getTransaction().commit();
        } catch (NoResultException | HibernateException e) {
            log.error("Find By Id Exception: " + e.getMessage());
        }
        return Optional.ofNullable(user);
    }
}
