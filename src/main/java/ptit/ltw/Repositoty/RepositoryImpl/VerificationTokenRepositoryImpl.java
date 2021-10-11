package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Repositoty.IRepository.VerificationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
@Slf4j
@Transactional
public class VerificationTokenRepositoryImpl extends CrudCustomRepositoryImpl<VerificationToken,Long> implements VerificationTokenRepository {

    public VerificationTokenRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
      return findByNaturalId(VerificationToken.class,"token",token);
    }
    @Override
    public void setConfirmAt(Long id,LocalDateTime confirmTime) {
        Transaction tr = null;
        try (Session session = sessionFactory.openSession()) {
            VerificationToken verificationToken = session.getReference(VerificationToken.class, id);
            verificationToken.setConfirmedAt(confirmTime);
            tr = session.beginTransaction();
            session.update(verificationToken);
            tr.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if (tr != null && tr.isActive()) tr.rollback();
            e.printStackTrace();
        }
    }

}
