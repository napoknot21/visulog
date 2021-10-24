package up.visulog.gitrawdata;

public class CommitBuilder extends Builder {

    public CommitBuilder(String id) {
        super(id);
    }   //Constructeur à partir de l'id

    public Commit createCommit() { //Cree un commit avec le build du commit
        return new Commit(getId(), getAuthor(), getDate(), getDescription(), getMergedFrom());
    }
}