package up.visulog.gitrawdata;
import java.math.BigInteger;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommitBuilder {
    private final BigInteger id;    //identifiant du commit
    private String author;      //auteur du commit
    private Date date;        //date de publication du commit
    private String description; //Description du commit
    // dont provient le commit

    public CommitBuilder(String idS) {
        BigInteger n = new BigInteger(idS, 16);
        this.id = n;
    }   //Constructeur à partir de l'id

    public BigInteger getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public CommitBuilder setAuthor(String author) { //setter
        this.author = author;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public void setId(String idS) {
    }

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


    public CommitBuilder setDate(Date date) {//setter
        this.date = date;
        return this;
    }

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



    public String getDescription() {
        return description;
    }

    public CommitBuilder setDescription(String description) {//setter
        this.description = description;
        return this;
    }

    public Commit createCommit() { //Cree un commit avec le build du commit
        return new Commit(getId(), getAuthor(), getDate(), getDescription());
    }

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
