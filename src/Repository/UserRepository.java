package Repository;

import Model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    User findById(UUID id);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAll();

}
