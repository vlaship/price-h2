package vlaship.price.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlaship.price.h2.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
