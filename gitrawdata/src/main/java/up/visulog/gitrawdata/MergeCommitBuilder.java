package up.visulog.gitrawdata;

public class MergeCommitBuilder extends CommitBuilder {
    private String mergedFrom;  //indique le dernier commit (commit parent) de la branche

    public MergeCommitBuilder(String id) {
        super(id);
    }

    public String getMergedFrom() {
        return mergedFrom;
    }

    public CommitBuilder setMergedFrom(String mergedFrom) {//setter
        this.mergedFrom = mergedFrom;
        return this;
    }

    /*public MergeCommit createCommit() { //Cree un commit avec le build du commit
        return new MergeCommit(getId(), getAuthor(), getDate(), getDescription(),getMergedFrom());
    }TODO: Decommenter la methode lorsque la classe MergeCommit sera crée */
}
