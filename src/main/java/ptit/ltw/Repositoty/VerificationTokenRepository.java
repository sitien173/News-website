package ptit.ltw.Repositoty;

import ptit.ltw.Entity.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenRepository extends CrudCustomRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
    void setConfirmAt(Long id, LocalDateTime confirmTime);
}
