package up.visulog.gitrawdata;

import java.util.Date;

public class CommitBuilder extends Builder {
    private String mergedFrom;  //indique la branche dont provient le commit

    public CommitBuilder(String id) {
        super(id);
    }   //Constructeur à partir de l'id

    public CommitBuilder setAuthor(String author) { //setter
        return (CommitBuilder) super.setAuthor(author);
    }

    public CommitBuilder setDate(Date date) {//setter
        return (CommitBuilder) super.setDate(date);
    }

    @Override
    public CommitBuilder setDate(String dateSt) { //convertit un String en Sate, setter
        return (CommitBuilder) super.setDate(dateSt);
    }

    @Override
    public CommitBuilder setDescription(String description) {//setter
        return (CommitBuilder) super.setDescription(description);
    }

    public CommitBuilder setMergedFrom(String mergedFrom) {//setter
        this.mergedFrom = mergedFrom;
        return this;
    }

    public Commit createCommit() { //Cree un commit avec le build du commit
        return new Commit(getId(), getAuthor(), getDate(), getDescription(), mergedFrom);
    }
}