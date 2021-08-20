package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.User;
import ptit.ltw.Repositoty.UserRepository;
import ptit.ltw.Utils.HibernateUtils;

import javax.management.Query;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
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
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
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
    public Optional<User> findByPhone(String phone) {
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
}
