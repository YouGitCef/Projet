import java.sql.Connection;

public class Commande {
    private static int id;
    private String userCommande;
    private String articleName;
    private int totalprix;

    public Commande(String userCommande, String articleName, int totalprix) {
        this.userCommande = userCommande;
        this.articleName = articleName;
        this.totalprix = totalprix;
        id++;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getUserCommande() {
        return userCommande;
    }

    public void setUserCommande(String userCommande) {
        this.userCommande = userCommande;
    }


    public int getTotalprix() {
        return totalprix;
    }

    public void setTotalprix(int totalprix) {
        this.totalprix = totalprix;
    }


}
