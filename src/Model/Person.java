package Model;

import java.util.UUID;

public abstract class  Person {

    protected UUID id;
    protected String fullName;
    protected String email;
    protected String phone;
    protected String password;

    public Person (UUID id , String fullName , String email , String phone , String password){
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getFullName(){
        return this.fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract String getRole();


}
