package Service;

import Model.User;
import Repository.impl.InMemoryUserRepository;
import utils.ValidationUtils;
import Exception.EmailAlreadyExistsException;
import Exception.InvalidCredentialsException;

import java.util.UUID;

public class AuthService {
    private final InMemoryUserRepository repo;
    private static User userLogin = null;

    public AuthService(){
        this.repo = new InMemoryUserRepository();
    }

    public InMemoryUserRepository getRepo() {
        return repo;
    }

    public  User Register(String fullName , String email, String phone , String password)  {
      if(!ValidationUtils.isValidName(fullName)){
          throw new IllegalArgumentException("Name invalide");
      }
      if(!ValidationUtils.isValidEmail(email)){
          throw new IllegalArgumentException("Email invalide");
      }
      if (!ValidationUtils.isValidPhone(phone)){
         throw new IllegalArgumentException("Phone invalide");
      }
      if(!ValidationUtils.isValidPassword(password)){
          throw new IllegalArgumentException("Password invalide");
      }
      if(repo.existsByEmail(email)){
          throw new EmailAlreadyExistsException("Email déja exist");
      }
      User user = new User(UUID.randomUUID(),fullName ,email ,phone , password );
      this.repo.save(user);

      return user;
    }

    public User Login(String email , String password){
        if(!ValidationUtils.isValidEmail(email)){
            throw new IllegalArgumentException("Email invalide");
        }
        if(!repo.existsByEmail(email)){
            throw new InvalidCredentialsException("Email n'éxist pas");
        }
        User user = repo.findByEmail(email);
        if(!user.getPassword().equals(password)){
            throw new InvalidCredentialsException("Password incorect");
        }
        userLogin = user;
        return user;
    }

    public User getUserLogin(){
        return userLogin;
    }
    public void setUserLogin(User user){
        userLogin = user;
    }

    public  boolean isLogin(){
        return userLogin != null;
    }

    public void logOut(){
        userLogin = null;
    }

    public void updateProfile(String fullName , String email , String phone){
        User user = this.getUserLogin();

        if(!ValidationUtils.isValidName(fullName)){
            throw new IllegalArgumentException("Name invalide");
        }
        if(!ValidationUtils.isValidEmail(email)){
            throw new IllegalArgumentException("Email invalide");
        }
        if (!ValidationUtils.isValidPhone(phone)){
            throw new IllegalArgumentException("Phone invalide");
        }
        if(repo.existsByEmail(email) && !user.getEmail().equals(email)){
            throw new EmailAlreadyExistsException("Email déja exist");
        }
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        repo.update(user);
    }

    public void UpdatePassword(String password,String oldPassword){
        User user = this.getUserLogin();
        if(!ValidationUtils.isValidPassword(password)){
            throw new IllegalArgumentException("Password invalide");
        }
        if(!user.getPassword().equals(oldPassword)){
            throw new InvalidCredentialsException("Password incorect");
        }
        user.setPassword(password);
        repo.update(user);
    }


}
