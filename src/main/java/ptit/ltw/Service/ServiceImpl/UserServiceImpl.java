package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Repositoty.IRepository.UserRepository;
import ptit.ltw.Repositoty.IRepository.VerificationTokenRepository;
import ptit.ltw.Service.IService.FileStoreService;
import ptit.ltw.Service.IService.MailService;
import ptit.ltw.Service.IService.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment environment;
    private final MailService mailService;
    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public AppUser findById(Long id) {
       return userRepository.findById(AppUser.class,id)
               .orElseThrow(() ->
                       new IllegalStateException(String.format("Id %s not found",id)));
    }

    @Override
    public List<AppUser> getAll() {
        return new ArrayList<>(userRepository.getAll(AppUser.class));
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(AppUser.class,id);
    }

    public void forgotPassword(String email) {
        AppUser appUser = userRepository.findByNaturalId(AppUser.class,"email",email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found", email)));
        VerificationToken verificationToken = new VerificationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusMinutes(Integer.parseInt(environment.getProperty("token.expiredAt"))),
                null,
                appUser
        );
        verificationTokenRepository.save(verificationToken);
        // TODO: send mail token
        String link = environment.getProperty("base.url") + "/forgot-password/confirm?token=" + verificationToken.getToken();
        mailService.sendMail(email, "Confirm Token to get password",
                buildEmail(appUser.getEmail(), link));
        log.info("Auto generator link authentication:  " + link);
    }

    @Override
    public void updatePassword(String email, String password) {
        AppUser appUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found", email)));
        appUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException(String.format("%s not found",email)));
    }

    @Override
    public void save(@NotNull AppUser appUser) {
         // TODO: encode password
        String passwordEncode = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(passwordEncode);
        // TODO: insert verificationToken and send mail to active account
        if(!appUser.isEnabled()) {
            VerificationToken verificationToken = sendMailRegistration(appUser, appUser.getEmail());
            appUser.addVerificationToken(verificationToken);
        }
        userRepository.save(appUser);
    }

    @Override
    public void update(AppUser appUser) {
        userRepository.save(appUser);
    }

    @Override
    public void setAuthentication(HttpSession session, AppUser appUser) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(appUser.getEmail(), null, appUser.getAuthorities());
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext);
    }

    private VerificationToken sendMailRegistration(AppUser appUser, String email) {
        VerificationToken verificationToken = new VerificationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusMinutes(Integer.parseInt(environment.getProperty("token.expiredAt"))),
                null,
                appUser
        );
        // TODO: send mail token authentication account
        String link = environment.getProperty("base.url") + "/registration/confirm?token=" + verificationToken.getToken();
        mailService.sendMail(email, "Confirm Token to enable account",
                buildEmail(email, link));
        log.info("Auto generator link authentication:  " + link);
        return verificationToken;
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in "+environment.getProperty("token.expiredAt")+" minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
