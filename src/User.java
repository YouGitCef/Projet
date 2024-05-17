import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private static int id;
    private String Login;
    private String Password;
    private String nom;
    private String prenom;
    private int role;


    public User(String login, String password, String nom,String prenom, int role) {
        this.Login = login;
        this.Password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        id++;

    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void DeleteUser(Connection con){
        System.out.println("write the user you want to delete");
        Scanner sc = new Scanner(System.in);
        String login = sc.nextLine();

        if(this.role == 1){
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("delete from users where login = '"+login+"'");
                System.out.println("User "+login+" deleted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("you are not alllowed to make this action");
        }
    }
    public void CreateUser(Connection con) {
        if(this.role == 1){
            Scanner sc = new Scanner(System.in);
            System.out.println("write the login you want to create");
            String login = sc.nextLine();
            System.out.println("write the password you want to create");
            String password = sc.nextLine();
            System.out.println("write the name");
            String name = sc.nextLine();
            System.out.println("write the surname");
            String surname = sc.nextLine();
            System.out.println("write the role");
            int role = sc.nextInt();
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("insert into user values('"+id+"','"+login+"','"+password+"','"+name+"','"+surname+"','"+role+"')");
                System.out.println("User inserted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("you are not alllowed to make this action");
        }
    }


    public void GetUsers(Connection con) {
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet resultSet= stmt.executeQuery("select * from user");
            while(resultSet.next()){
                users.add(new User(resultSet.getString("login"),resultSet.getString("password"),resultSet.getString("nom"),resultSet.getString("prenom"),resultSet.getInt("role")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.forEach((user) -> System.out.println(user.toString()));
    }

    public void buyArticle(Connection con){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name article:");
        String article = sc.nextLine();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from article where nom = '"+article+"'");
            if(rs.next()){
                Article articleSelectionne =  new Article(rs.getString("nom"),rs.getString("marque"),rs.getInt("prix"),rs.getInt("quantite"));
                System.out.println("Enter quantity you want to buy:");
                int quantity = sc.nextInt();
                if (quantity <= articleSelectionne.getQuantite()){
                    try{
                        stmt = con.createStatement();
                        stmt.executeUpdate("update article set quantite = '"+(articleSelectionne.getQuantite() - quantity) + "' where name = '" + article + "'");
                        int totalPrix = quantity * articleSelectionne.getPrix();

                        Commande commande = new Commande(this.nom, articleSelectionne.getNom(), totalPrix);
                        stmt.executeUpdate("insert into user values('"+id+"','CURRENT_TIMESTAMP','"+commande.getUserCommande()+"','"+totalPrix+"','"+commande.getArticleName()+"')");
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Quantité insuffisante");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int MenuUser(){

        System.out.println("Welcome User");
        System.out.println("choose an action : ");
        System.out.println("66.Display all articles ");
        System.out.println("2.Make a purchase");
        System.out.println("0.Quit");

        int a;
        do {
            Scanner sc = new Scanner(System.in);
            a = sc.nextInt();
        }while(a<0 || a>2);

        return a;
    }

    public int MenuAdmin(){

        System.out.println("Welcome Admin");
        System.out.println("choose an action : ");
        System.out.println("11.Create a new user ");
        System.out.println("22.Delete a user ");
        System.out.println("33.Display all users");
        System.out.println("44.Create a new article ");
        System.out.println("55.Delete an article ");
        System.out.println("66.Display all articles");
        System.out.println("0.Quit");

        int a;
        do {
            Scanner sc = new Scanner(System.in);
            a = sc.nextInt();
        }while(a!=11 && a!=22 && a!=33  && a!=0);

        return a;
    }
}