package up.visulog.gitrawdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class Builder {
    private final String id;    //identifiant du commit
    private String author;      //auteur du commit
    private Date date;        //date de publication du commit
    private String description; //Description du commit

    public Builder(String id) {
        this.id = id;
    }   //Constructeur à partir de l'id


    public Builder setAuthor(String author) { //setter
        this.author = author;
        return this;
    }

    public Builder setDate(Date date) {//setter
        this.date = date;
        return this;
    }
    public Builder setDate(String dateSt) { //convertit un String en Sate, setter
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z",Locale.ENGLISH).parse(dateSt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = date;
        return this;
    }


    public Builder setDescription(String description) {//setter
        this.description = description;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
