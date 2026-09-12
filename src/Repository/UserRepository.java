package Repository;

import Model.Person;
import Model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void save(Person user);

    Optional<Person> findById(UUID id);

    Person findByEmail(String email);

    boolean existsByEmail(String email);

    List<Person> findAll();

    void update(Person user);
}
