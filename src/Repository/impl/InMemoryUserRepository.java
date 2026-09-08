package Repository.impl;

import Model.User;
import Repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private HashMap<UUID , User> users = new HashMap<>();

    @Override
    public void save(User user){
        this.users.put(user.getId() , user);
    };

    @Override
   public User findById(UUID id){
        return this.users.get(id);
    };

    @Override
    public User findByEmail(String email){
      for(User user : users.values()){
          if(user.getEmail().equals(email)){
              return user;
          }
      }
      return null;
    };

    @Override
    public  boolean existsByEmail(String email){
      for(User user : users.values()){
          if(user.getEmail().equals(email)){
              return true;
          }
      }
        return false;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();

        for (User user : users.values()) {
            result.add(user);
        }

        return result;
    }

}
