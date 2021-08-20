package ptit.ltw.Repositoty;

import ptit.ltw.Entity.VerificationToken;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface VerificationTokenRepository {
    void save(@NotNull VerificationToken verificationToken);
    Optional<VerificationToken> findByToken(String token);
    void setConfirmAt(Long id);
}
