package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        Transaction tr = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.saveOrUpdate(verificationToken);
            tr.commit();
            session.close();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            log.error("Save VerificationToken Exception: " + e.getMessage());
        }
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        Optional<VerificationToken> verificationToken = Optional.empty();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
           verificationToken = session.byNaturalId(VerificationToken.class).using("token",token)
                    .loadOptional();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            log.error("Save VerificationToken Exception: " + e.getMessage());
        }
        return verificationToken;
    }
    @Override
    public void setConfirmAt(Long id) {
        Transaction tr = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            VerificationToken verificationToken = session.get(VerificationToken.class, id);
            verificationToken.setConfirmedAt(LocalDateTime.now());
            tr = session.beginTransaction();
            session.update(verificationToken);
            tr.commit();
            session.close();
        } catch (NullPointerException | HibernateException e) {
            if(tr != null) tr.rollback();
            log.error("setConfirmAt Exception: " + e.getMessage());
        }
    }
}
