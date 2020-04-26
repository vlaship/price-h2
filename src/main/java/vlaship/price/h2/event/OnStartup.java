package vlaship.price.h2.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vlaship.price.h2.entity.User;
import vlaship.price.h2.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class OnStartup {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @EventListener(ApplicationReadyEvent.class)
    public void addUser(@Value("user.login") String login, @Value("user.pass") String pass) {
        userRepository.save(User.builder().name(login).password(encoder.encode(pass)).build());
    }

}
