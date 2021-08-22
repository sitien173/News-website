package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptit.ltw.Converter.VerificationTokenConverter;
import ptit.ltw.Dto.VerificationTokenDto;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Repositoty.UserRepository;
import ptit.ltw.Repositoty.VerificationTokenRepository;
import ptit.ltw.Service.VerificationTokenService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final VerificationTokenConverter verificationTokenConverter;

    @Override
    public VerificationTokenDto findByToken(String token) {
        return verificationTokenConverter.vfTokenEntityToDto(verificationTokenRepository
                .findByToken(token)
                    .orElseThrow(() ->
                        new IllegalStateException(String.format("token %s not found",token))));
    }

    @Override
    public VerificationTokenDto confirmToken(String token) {
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
        verificationTokenRepository.setConfirmAt(verificationToken.getId());
        if (!verificationToken.getUser().isEnabled())
            verificationToken.getUser().setIsEnable(true);
        userRepository.update(verificationToken.getUser());
        return verificationTokenConverter.vfTokenEntityToDto(verificationToken);
    }
}
