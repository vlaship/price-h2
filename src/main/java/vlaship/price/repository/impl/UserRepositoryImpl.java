package vlaship.price.repository.impl;

import org.springframework.stereotype.Service;
import vlaship.price.entity.User;
import vlaship.price.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> map = new HashMap<>();
    private final ReentrantReadWriteLock.WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
    private final ReentrantReadWriteLock.ReadLock readLock = new ReentrantReadWriteLock().readLock();

    @Override
    public Optional<User> findByName(final String name) {
        readLock.lock();
        try {
            return map.entrySet().stream()
                    .filter(e -> e.getKey().equals(name))
                    .map(Map.Entry::getValue)
                    .findFirst();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void add(User user) {
        writeLock.lock();
        try {
            map.put(user.getName(), user);
        } finally {
            writeLock.unlock();
        }
    }
}
