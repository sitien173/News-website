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
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Repositoty.VerificationTokenRepository;
import ptit.ltw.Utils.HibernateUtils;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Log4j2
@AllArgsConstructor
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {
    private final SessionFactory sessionFactory;
    @Override
    public void save(@NotNull VerificationToken verificationToken){
        Session session =  null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            session.saveOrUpdate(verificationToken);
            tr.commit();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        Session session =  null;
        Transaction tr = null;
        Optional<VerificationToken> verificationToken = Optional.empty();
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
           verificationToken = session.byNaturalId(VerificationToken.class)
                    .using("token",token)
                    .loadOptional();
            tr.commit();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
        return verificationToken;
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
