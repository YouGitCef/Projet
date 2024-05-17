import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static User auth(Connection con){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter login: ");
        String login = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs =stmt.executeQuery("select * from user where Login = '"+login+"' and password = '"+password+"'");
            if(rs.next()){
                return new User(rs.getString("login"),rs.getString("password"),rs.getString("nom"),rs.getString("prenom"),rs.getInt("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/db1?user=root";
        String user = "root";
        String passwd = "rootroot";

        Class.forName("com.mysql.cj.jdbc.Driver"); // Driver name
        Connection con = DriverManager.getConnection(url, user, passwd);
        System.out.println("Connection Established successfully");

        User connectedUser = auth(con);
        while(connectedUser == null){
            connectedUser = auth(con);
        }
        int a;
        do {
            if(connectedUser.getRole() == 1){
                a = connectedUser.MenuAdmin();
            }else {
                a = connectedUser.MenuUser();
            }
            switch(a){
                case 2:
                    connectedUser.buyArticle(con);
                    break;
                case 11:
                    connectedUser.CreateUser(con);
                    break;
                case 22:
                    connectedUser.DeleteUser(con);
                    break;
                case 33:
                    connectedUser.GetUsers(con);
                    break;
                case 44:
                    Article.CreateArticle(con, connectedUser.getRole());
                    break;
                case 66:
                    Article.GetArticle(con);
                    break;
            }
        }while (a!=0);
    }
}