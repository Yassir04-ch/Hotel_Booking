package Model;
import java.util.UUID;
public class User extends Person{

    public User(UUID id , String fullName , String email , String phone , String password){
       super(id , fullName , email , phone , password);
    }

   @Override
    public String getRole(){
        return "user";
    }

}
