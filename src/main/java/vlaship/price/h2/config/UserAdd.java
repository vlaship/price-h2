package vlaship.price.h2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import vlaship.price.h2.entity.Role;
import vlaship.price.h2.entity.User;
import vlaship.price.h2.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAdd {

    @Value("${default-user.login}")
    private String login;
    @Value("${default-user.pass}")
    private String pass;

    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void addUser() {
        if (userRepository.count() > 0) return;

        log.warn("Adding default user");

        final var encoder = new BCryptPasswordEncoder();

        final var user = User.builder()
                .name(login)
                .password(encoder.encode(pass))
                .role(Role.ADD)
                .build();

        userRepository.save(user);

        log.warn("Default user is added");

    }
}
