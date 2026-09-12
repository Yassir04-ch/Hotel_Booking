package Repository.impl;

import Model.Person;
import Model.User;
import Repository.UserRepository;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {

    private HashMap<UUID , Person> users = new HashMap<>();

    @Override
    public void save(Person user){
        this.users.put(user.getId() , user);
    };

    @Override
   public Optional<Person> findById(UUID id){
        return Optional.ofNullable(users.get(id));
    };

    @Override
    public Person findByEmail(String email){
      for(Person user : users.values()){
          if(user.getEmail().equals(email)){
              return user;
          }
      }
      return null;
    };

    @Override
    public  boolean existsByEmail(String email){
      for(Person user : users.values()){
          if(user.getEmail().equals(email)){
              return true;
          }
      }
        return false;
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();

        for (Person user : users.values()) {
            result.add(user);
        }

        return result;
    }

    @Override
    public void update(Person user){
        this.users.put(user.getId() , user);
    }

}
