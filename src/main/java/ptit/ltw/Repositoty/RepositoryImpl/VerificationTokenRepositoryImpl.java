package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Repositoty.VerificationTokenRepository;
import ptit.ltw.Utils.HibernateUtils;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Log4j2
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {
    @Override
    public void save(@NotNull VerificationToken verificationToken) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(verificationToken);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Save VerificationToken Exception: " + e.getMessage());
        }
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        VerificationToken verificationToken = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "from VerificationToken where token = :token";
            verificationToken = session.createQuery(HQL, VerificationToken.class)
                    .setParameter("token", token).getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException | HibernateException e) {
            log.error("Find By Token Exception: " + e.getMessage());
        }
        return Optional.ofNullable(verificationToken);
    }
    @Override
    public void setConfirmAt(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            VerificationToken verificationToken = session.get(VerificationToken.class, id);
            session.beginTransaction();
            verificationToken.setConfirmedAt(LocalDateTime.now());
            session.update(verificationToken);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("setConfirmAt Exception: " + e.getMessage());
        }
    }
}
