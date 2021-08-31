package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Repositoty.VerificationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Log4j2
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
        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            VerificationToken verificationToken = session.getReference(VerificationToken.class, id);
            verificationToken.setConfirmedAt(confirmTime);
            tr = session.beginTransaction();
            session.update(verificationToken);
            tr.commit();
        } catch (HibernateException e) {
            if (tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

}
