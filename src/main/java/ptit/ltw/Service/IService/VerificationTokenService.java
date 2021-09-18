package ptit.ltw.Service.IService;


import ptit.ltw.Entity.VerificationToken;


public interface VerificationTokenService {
    VerificationToken confirmToken(String token);
}
