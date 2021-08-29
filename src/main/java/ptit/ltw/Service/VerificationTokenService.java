package ptit.ltw.Service;


import ptit.ltw.Entity.VerificationToken;


public interface VerificationTokenService {
    VerificationToken confirmToken(String token);
}
