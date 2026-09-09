import Model.Room;
import Model.User;
import Service.AuthService;

import java.math.BigDecimal;
import java.util.List;
import  Exception.EmailAlreadyExistsException;
import  Exception.InvalidCredentialsException;
import Service.RoomService;
import utils.InputUtils;

public class Main {

    private static AuthService Authservice;
    private static RoomService RoomService;

    public static int menuAuth() {
        System.out.println(" ===== Menu ===== ");
        System.out.println("1-Register");
        System.out.println("2-Login");
        System.out.println("3-Exite");
        int choix = InputUtils.readInt("Entrer Votre Choix : ");
        return choix;
    }

    public static void menuLogin() {
        while(true) {
            try {
                String email = InputUtils.readString("Email");
                String password = InputUtils.readString("Mode passe");
                User user = Authservice.Login(email, password);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");

            } catch (InvalidCredentialsException e) {

                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");

            }
        }
    }

    public static void saveRoom(){
        Room room1 =  new Room( "101", "Single", 1, new BigDecimal("500.00"), "AVAILABLE");
        Room room2 =  new Room( "303", "Double", 2, new BigDecimal("800.00"), "OCCUPIED");
        RoomService.getRepo().save(room1);
        RoomService.getRepo().save(room2);
    }

    public static void afficherRooms(){
        List<Room> rooms = RoomService.getAllRooms();
        if (rooms.isEmpty()){
            System.out.println("Aucune chambre disponible.");
            return;
        }
        for(Room room : rooms){
            System.out.println("=======================");
            System.out.println("roomNumber : " + room.getRoomNumber());
            System.out.println("capacity : " + room.getCapacity());
            System.out.println("price : " + room.getPrice());
            System.out.println("type : " + room.getType());
            System.out.println("status : " + room.getStatus());
            System.out.println("=======================");
        }

    }

    public static void menuRegister() {


        while (true) {
            try {
                String fullName = InputUtils.readString("FullName : ");

                System.out.println("Phone : ");
                String phone = InputUtils.readString("Phone : ");

                String email = InputUtils.readString("Email : ");
                String password = InputUtils.readString("Mode passe : ");

                User user = Authservice.Register(fullName, email, phone, password);

                System.out.println("Register réussi !");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");
            } catch (EmailAlreadyExistsException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");
            }
        }
    }

    public static void menuProfile(){
        User user = Authservice.getUserLogin();
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
            Authservice.updateProfile(name, email, phone);
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
          Authservice.UpdatePassword(password,oldPassword);

      }catch (IllegalArgumentException e){
         System.out.println("Erreur : " + e.getMessage());
      }catch (InvalidCredentialsException e){
          System.out.println("Erreur : " + e.getMessage());
      }
    }

    public static void menuPrincipale(){
        while (true){
        System.out.println("1. Search available rooms");
        System.out.println("2. View all rooms");
        System.out.println("3. Create reservation");
        System.out.println("4. My reservations");
        System.out.println("5. Reservation details");
        System.out.println("6. Update reservation");
        System.out.println("7. Cancel reservation");
        System.out.println("8. Update profile");
        System.out.println("9. Change password");
        System.out.println("10. Afficher Profile");
        System.out.println("11. Logout");
        System.out.println("0. Exit");

        int choix = InputUtils.readInt("Entrer une Choix");
        switch (choix) {
            case 1:
                break;
            case 2:
                saveRoom();
                afficherRooms();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                menuProfile();
                break;
            case 11:
                Authservice.logOut();
                System.out.println("Logout");
                return;
            default:
                break;
        }
    }
    }


    static void main(String[] args) {
        Authservice = new AuthService();
        RoomService = new RoomService();
            while(true) {
                int choix = menuAuth();
                switch (choix) {
                    case 1:
                        System.out.println("Register");
                        menuRegister();
                        break;
                    case 2:
                        System.out.println("Login");
                        menuLogin();
                        System.out.println("Welcome ");
                        menuPrincipale();
                        break;
                    default:
                        System.out.println("Good Day");
                        break;
                }
            }
        }
}
