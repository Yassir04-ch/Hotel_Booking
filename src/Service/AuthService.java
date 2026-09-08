package Service;

import Model.User;
import Repository.impl.InMemoryUserRepository;
import utils.ValidationUtils;
import Exception.EmailAlreadyExistsException;
import Exception.InvalidCredentialsException;

import java.util.UUID;

public class AuthService {
    private InMemoryUserRepository repo;
    private User userLogin = null;

    public AuthService(InMemoryUserRepository repo){
        this.repo = repo;
    }

    public User Register(String fullName , String email, String phone , String password)  {
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
        this.userLogin = user;
        return user;
    }

    public User getUserLogin(){
        return this.userLogin;
    }
    public void setUserLogin(User user){
        this.userLogin = user;
    }

    public boolean isLogin(){
        return userLogin != null;
    }

    public void logOut(){
        this.userLogin = null;
    }

}
