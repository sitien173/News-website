package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Repositoty.IRepository.UserRepository;
import ptit.ltw.Repositoty.IRepository.VerificationTokenRepository;
import ptit.ltw.Service.IService.VerificationTokenService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;


    @Override
    public VerificationToken confirmToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.
                findByToken(token)
                    .orElseThrow(() -> new IllegalStateException(String.format("token %s not found",token)));
        if (verificationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = verificationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        verificationTokenRepository.setConfirmAt(verificationToken.getId(), LocalDateTime.now());
        if (!verificationToken.getAppUser().isAccountNonLocked()){
            verificationToken.getAppUser().setIsAccountNonLocked(true);
            userRepository.save(verificationToken.getAppUser());
        }
       return verificationToken;
    }
}
