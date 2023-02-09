package up.visulog.gitrawdata;

public class MergeCommitBuilder extends CommitBuilder {

    private String mergedFrom;

    /**
     * Constructeur de MergeCommitBuilder
     * @param id L'id du commit
     */
    public MergeCommitBuilder(String id) {
        super(id);
    }

    /**
     * Getter pour le dernier commit de la branche
     * @return Le dernier commit
     */
    public String getMergedFrom() {
        return mergedFrom;
    }

    /**
     * Setter pour le dernier commit de la branche
     * @param mergedFrom représente le nouveau commit parent
     * @return le nouveau commit
     */
    public CommitBuilder setMergedFrom(String mergedFrom) {
        this.mergedFrom = mergedFrom;
        return this;
    }

    /**
     * Modifie le merge commit, ou ne fait rien
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
     * Crée un commit à l'aide du build du commit
     * @return Objet MergeCommit
     * @see CommitBuilder
     */
    public MergeCommit createCommit() { //Cree un commit avec le build du commit
        return new MergeCommit(getId(), getAuthor(), getDate(), getDescription(),getMergedFrom());
    }
}
