import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Article {
    private static int id;
    private String nom;
    private String marque;
    private int prix;
    private int quantite;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Article(String nom, String marque, int prix, int quantite){
        id++;
        this.nom = nom;
        this.marque = marque;
        this.prix = prix;
        this.quantite = quantite;
    }

    public void deleteArticle(Connection con, int role){
        System.out.println("write the article you want to delete");
        Scanner sc = new Scanner(System.in);
        String nom = sc.nextLine();

        if(role == 1){
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("delete from users where login = '"+nom+"'");
                System.out.println("Article "+nom+" deleted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("you are not alllowed to make this action");
        }
    }

    public void CreateArticle(Connection con, int role) {
        if(role == 1){
            Scanner sc = new Scanner(System.in);
            System.out.println("write the name of article you want to create");
            String nom = sc.nextLine();
            System.out.println("write the marque of article you want to create");
            String marque = sc.nextLine();
            System.out.println("write the price's article");
            int prix = sc.nextInt();
            System.out.println("write the quantity's article");
            int quantite = sc.nextInt();
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("insert into article values('"+id+"','"+nom+"','"+marque+"','"+prix+"','"+quantite+"')");
                System.out.println("Article inserted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("you are not alllowed to make this action");
        }
    }

    public void GetArticle(Connection con) {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet resultSet= stmt.executeQuery("select * from article");
            while(resultSet.next()){
                articles.add(new Article(resultSet.getString("nom"),resultSet.getString("marque"),resultSet.getInt("prix"),resultSet.getInt("quantite")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        articles.forEach((user) -> System.out.println(user.toString()));
    }
}
