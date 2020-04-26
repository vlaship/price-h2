package vlaship.price.h2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vlaship.price.h2.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SimpleDbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new SimpleDbUserPrinciple(user);
    }
}
