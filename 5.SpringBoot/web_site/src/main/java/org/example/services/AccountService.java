package org.example.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.ForgotPasswordDTO;
import org.example.data.data_transfer_objects.RegisterUserDTO;
import org.example.data.data_transfer_objects.ResetPasswordDTO;
import org.example.data.smtp.EmailMessage;
import org.example.entities.RoleEntity;
import org.example.entities.UserEntity;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;
    private final FileService fileService;

    public boolean registerUser(RegisterUserDTO dto, HttpServletRequest request) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            return false;
        }

        String fileName = fileService.load(dto.getImageFile());

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setImage(fileName);
        user.setEmail(dto.getEmail());

        Optional<RoleEntity> userRoleOpt = roleRepository.findByName("User");

        if (userRoleOpt.isPresent()) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(userRoleOpt.get());
            user.setRoles(roles);
        }

        userRepository.save(user);

        //щоб була авторизація після реєстрації
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return true;
    }

    public List<UserEntity> GetAllUsers() {
        return userRepository.findAll();
    }

    public boolean forgotPassword(ForgotPasswordDTO dto, HttpServletRequest request) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) {
            return false;
        }

        UserEntity user = userOpt.get();

        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        userRepository.save(user);

        String siteUrl = getSiteUrl(request);

        String resetLink = siteUrl + "/reset-password?token=" + token;

        String subject = "Відновлення паролю";
        String body = """
            <div style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 30px;">
                <div style="max-width: 600px; margin: auto; background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <div style="background: #007bff; color: white; padding: 15px; text-align: center;">
                        <h2>Відновлення паролю</h2>
                    </div>
                    <div style="padding: 20px;">
                        <p>Вітаємо, <strong>%s</strong>!</p>
                        <p>Ми отримали запит на відновлення вашого паролю. Натисніть кнопку нижче, щоб задати новий пароль:</p>
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="%s" style="background-color: #007bff; color: white; padding: 12px 25px; border-radius: 5px; text-decoration: none; font-size: 16px;">Скинути пароль</a>
                        </div>
                        <p>Або скопіюйте це посилання у браузер:</p>
                        <p><a href="%s">%s</a></p>
                        <p style="color: #888;">Якщо ви не надсилали запит, просто ігноруйте цей лист.</p>
                    </div>
                    <div style="background: #f0f0f0; color: #555; padding: 10px; text-align: center; font-size: 12px;">
                        © %d Your Company. Усі права захищено.
                    </div>
                </div>
            </div>
            """.formatted(user.getUsername(), resetLink, resetLink, resetLink, Calendar.getInstance().get(Calendar.YEAR));

        EmailMessage email = new EmailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setBody(body);

        SmtpService smtpService = new SmtpService();
        boolean sent = smtpService.sendEmail(email);

        if (sent) {
            System.out.println("Reset password email sent to " + user.getEmail());
        } else {
            System.err.println("Failed to send email to " + user.getEmail());
        }

        return sent;
    }

    private String getSiteUrl(HttpServletRequest request) {
        String scheme = request.getScheme();             // http або https
        String serverName = request.getServerName();     // localhost або myapp.com
        int serverPort = request.getServerPort();        // 8080 або 443
        String contextPath = request.getContextPath();   // якщо додаток не в корені

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ((scheme.equals("http") && serverPort != 80) ||
                (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath);
        return url.toString();
    }


    public boolean resetPassword(ResetPasswordDTO dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            return false;
        }

        Optional<UserEntity> userOpt = userRepository.findByResetPasswordToken(dto.getToken());
        if (userOpt.isEmpty()) {
            return false;
        }

        UserEntity user = userOpt.get();
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setResetPasswordToken(null);
        userRepository.save(user);

        return true;
    }
}