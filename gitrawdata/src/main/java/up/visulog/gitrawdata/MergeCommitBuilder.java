package up.visulog.gitrawdata;

public class MergeCommitBuilder extends Builder {

    public MergeCommitBuilder(String id) {
        super(id);
    }
    /*public MergeCommit createMergeCommit() { //Cree un commit avec le build du commit
        return new MergeCommit(getId(), getAuthor(), getDate(), getDescription(),getMergedFrom());
    }TODO: Decommenter la methode lorsque la classe MergeCommit sera crée */
}
