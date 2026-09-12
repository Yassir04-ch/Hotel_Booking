package Main;

import Model.User;
import Service.AuthService;
import Exception.EmailAlreadyExistsException;
import Exception.InvalidCredentialsException;
import utils.InputUtils;

public class ProfileMenu {

    public static void menuProfile(){
        User user = Main.Authservice.getUserLogin();
        boolean ret = true;
        while (ret) {

            System.out.println("Nom : " + user.getFullName());
            System.out.println("Phone : " + user.getPhone());
            System.out.println("Email : " + user.getEmail());
            System.out.println("Password : " + user.getPassword());
            System.out.println("1-Modifier Profile");
            System.out.println("2-Modifier password");
            System.out.println("3-Return");
            int choix = InputUtils.readInt("Entrer votre choix");
            switch (choix) {
                case 1:
                    menuUpdateProfile();
                    break;
                case 2:
                    updatePassword();
                    break;
                case 3:
                    ret = false;
                    break;
                default:
                    System.out.println("choix invalide");
                    break;
            }
        }
    }

    public static void menuUpdateProfile(){
        try {
            System.out.println("==== Modifier Profile ====");
            String name = InputUtils.readString("Entrer  nom : ");
            String email = InputUtils.readString("Entrer  email : ");
            String phone = InputUtils.readString("Entrer phone  : ");
            Main.Authservice.updateProfile(name, email, phone);
            System.out.println("Votre profile et modifier");
        }catch (IllegalArgumentException e){
            System.out.println("Erreur : " +e.getMessage());
        }catch (EmailAlreadyExistsException e){
            System.out.println("Erreur : "+ e.getMessage());
        }
    }

    public static void updatePassword(){
        try{
            String oldPassword = InputUtils.readString("Entrer votre Mode passe : ");
            String password = InputUtils.readString("Entrer neuveaux Mode passe : ");
            Main.Authservice.UpdatePassword(password,oldPassword);

        }catch (IllegalArgumentException e){
            System.out.println("Erreur : " + e.getMessage());
        }catch (InvalidCredentialsException e){
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}