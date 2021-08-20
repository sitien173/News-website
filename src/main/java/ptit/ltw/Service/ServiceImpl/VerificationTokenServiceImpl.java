package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Repositoty.UserRepository;
import ptit.ltw.Repositoty.VerificationTokenRepository;
import ptit.ltw.Service.VerificationTokenService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public Boolean confirmToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.
                findByToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
        if (verificationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = verificationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        verificationTokenRepository.setConfirmAt(verificationToken.getId());
        if (!verificationToken.getUser().isEnabled())
            verificationToken.getUser().setIsEnable(true);
        userRepository.update(verificationToken.getUser());
        return true;
    }
}
