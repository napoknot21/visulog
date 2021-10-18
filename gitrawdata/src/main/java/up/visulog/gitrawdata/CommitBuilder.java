package up.visulog.gitrawdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommitBuilder {
    private final String id;    //identifiant du commit
    private String author;      //auteur du commit
    private Date date;        //date de publication du commit
    private String description; //Description du commit
    private String mergedFrom;  //indique la branche dont provient le commit

    public CommitBuilder(String id) {
        this.id = id;
    }   //Constructeur à partir de l'id

    public CommitBuilder setAuthor(String author) { //setter
        this.author = author;
        return this;
    }

    public CommitBuilder setDate(Date date) {//setter
        this.date = date;
        return this;
    }
    public CommitBuilder setDate(String dateSt) { //convertit un String en Sate, setter
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z",Locale.ENGLISH).parse(dateSt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = date;
        return this;
    }


    public CommitBuilder setDescription(String description) {//setter
        this.description = description;
        return this;
    }

    public CommitBuilder setMergedFrom(String mergedFrom) {//setter
        this.mergedFrom = mergedFrom;
        return this;
    }

    public Commit createCommit() { //Cree un commit avec le build du commit
        return new Commit(id, author, date, description, mergedFrom);
    }
}