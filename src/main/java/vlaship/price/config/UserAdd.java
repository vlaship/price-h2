package vlaship.price.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import vlaship.price.entity.User;
import vlaship.price.repository.UserRepository;

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
        log.warn("Adding default user");

        final var encoder = new BCryptPasswordEncoder();

        final var user = User.builder()
                .name(login)
                .password(encoder.encode(pass))
                .build();

        userRepository.add(user);

        log.warn("Default user is added");

    }
}
