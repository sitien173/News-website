package ptit.ltw.Service;

import ptit.ltw.Dto.VerificationTokenDto;


public interface VerificationTokenService {
    VerificationTokenDto confirmToken(String token);
    VerificationTokenDto findByToken(String token);

}
