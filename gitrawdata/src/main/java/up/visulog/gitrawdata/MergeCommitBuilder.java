package up.visulog.gitrawdata;

public class MergeCommitBuilder extends CommitBuilder {
    private String mergedFrom;  //indique le dernier commit (commit parent) de la branche

    public MergeCommitBuilder(String id) {
        super(id);
    }

    public String getMergedFrom() {
        return mergedFrom;
    }


    /**
     * Modifie le dernier commit de la branche
     * @param mergedFrom représente le nouveau commit parent
     * @return le nouveau commit
     */
    public CommitBuilder setMergedFrom(String mergedFrom) {//setter
        this.mergedFrom = mergedFrom;
        return this;
    }

    /**
     * Modifie le merge commit (ou ne fait rien)
     * @param fieldName représente l'attribut à modifier
     * @param fieldContent représente nouvelle valeur de cet attribut
     */
    @Override
    public void CommitConfig(String fieldName, String fieldContent){
        switch (fieldName) {
            case "Author":
                this.setAuthor(fieldContent);
                break;
            case "Merge":
                this.setMergedFrom(fieldContent);
                break;
            case "Date":
                this.setDate(fieldContent);
                break;
            default:
                System.out.println(fieldName + " is ignored");
        }
    }

    /**
     * Cree un commit à l'aide du build du commit
     * @see CommitBuilder
     */
    public MergeCommit createCommit() {
        return new MergeCommit(getId(), getAuthor(), getDate(), getDescription(),getMergedFrom());
    }
}
