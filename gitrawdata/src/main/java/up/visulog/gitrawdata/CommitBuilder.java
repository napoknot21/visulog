package up.visulog.gitrawdata;
import java.math.BigInteger;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommitBuilder {
    private final BigInteger id;    //identifiant du commit
    private String author;      //auteur du commit
    private Date date;        //date de publication du commit
    private String description; //Description du commit dont provient le commit

    private List<String> files = new ArrayList<String>();

    /**
     * Constructeur CommitBuilder à partir de l'id
     * @param idS L'identifiant du commit en format String
     */
    public CommitBuilder(String idS) {
        BigInteger n = new BigInteger(idS, 16);
        this.id = n;
    }

    /**
     * Getter pour l'id du CommitBuilder
     * @return L'identifiant du commit
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Getter pour l'auteur du CommitBuilder
     * @return L'auteur du commit
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter pour l'auteur du commit
     * @param author Le nom de l'auteur
     * @return le commit changé
     */
    public CommitBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    /**
     * Getter pour la date de CommitBuilder
     * @return La date du commit
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter pour l'identifiant du CommitBuilder
     * @param idS L'identifiant
     */
    public void setId(String idS) {
    }

    /**
     * Transforme un caractère (base 16) en base 10
     * @param c le caractère en base 16
     * @return la valeur en base 10
     */
    public BigInteger hexToTen(char c) {
        if (c < 10) {
            return BigInteger.valueOf(c);
        } if (c == 'a') {
            return BigInteger.TEN;
        } if (c == 'b') {
            return BigInteger.valueOf(11);
        } if (c == 'c') {
            return BigInteger.valueOf(12);
        } if (c == 'd') {
            return BigInteger.valueOf(13);
        } if (c == 'e') {
            return BigInteger.valueOf(14);
        } if (c == 'f') {
            return BigInteger.valueOf(15);
        } else {
            return null;
        }
    }

    /**
     * Modifie la date du commit
     * @param date représente la nouvelle date de type Date
     * @return le nouveau commit
     */
    public CommitBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Modifie la date du commit en convertissant une date en chaine de caractère en une date de type Date
     * @param dateSt représente la nouvelle date de type String
     * @return le nouveau commit
     */
    public CommitBuilder setDate(String dateSt) { //convertit un String en Sate, setter
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z", Locale.ENGLISH).parse(dateSt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = date;
        return this;
    }

    /**
     * Getter pour la description de CommitBuider
     * @return la description du commit
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifie la description du commit
     * @param description représente la nouvelle description
     * @return le nouveau commit
     */
    public CommitBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Crée une copie du commit
     * @return le nouveau commit crée
     */
    public Commit createCommit() {
        return new Commit(getId(), getAuthor(), getDate(), getDescription());
    }

    /**
     * Modifie soit le date soit l'auteur d'un commit (ou ne fait rien)
     * @param fieldName représente l'attribut à modifier
     * @param fieldContent représente nouvelle valeur de cet attribut
     */
    public void CommitConfig(String fieldName, String fieldContent){
        switch (fieldName) {
            case "Author":
                this.setAuthor(fieldContent);
                break;
            case "Date":
                this.setDate(fieldContent);
                break;
            default: System.out.println(fieldName +" is ignored");
        }
    }
}
