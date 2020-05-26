package vlaship.price.h2.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vlaship.price.h2.entity.Role;
import vlaship.price.h2.entity.User;
import vlaship.price.h2.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class OnStartup {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Value("${user.view.login}")
    private String viewLogin;
    @Value("${user.view.pass}")
    private String viewPass;
    @Value("${user.add.login}")
    private String addLogin;
    @Value("${user.add.pass}")
    private String addPass;

    @EventListener(ApplicationReadyEvent.class)
    public void addUser() {
        userRepository.save(User.builder().name(viewLogin).password(encoder.encode(viewPass)).role(Role.VIEW).build());
        userRepository.save(User.builder().name(addLogin).password(encoder.encode(addPass)).role(Role.ADD).build());
    }

}
