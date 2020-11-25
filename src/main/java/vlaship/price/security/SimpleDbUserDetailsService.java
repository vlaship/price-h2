package vlaship.price.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vlaship.price.repository.UserRepository;

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
