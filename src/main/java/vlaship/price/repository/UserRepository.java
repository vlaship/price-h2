package vlaship.price.repository;

import vlaship.price.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByName(String name);

    void add(User user);
}
