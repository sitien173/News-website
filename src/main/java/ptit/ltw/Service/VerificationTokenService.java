package ptit.ltw.Service;

import ptit.ltw.Entity.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {
    Boolean confirmToken(String token);
    Optional<VerificationToken> findByToken(String token);

}
