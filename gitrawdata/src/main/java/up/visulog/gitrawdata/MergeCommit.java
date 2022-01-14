package up.visulog.gitrawdata;

import java.math.BigInteger;
import java.util.Date;

public class MergeCommit extends Commit{
    public final String mergedFrom; // la branche d'où vient la modification

    public MergeCommit(BigInteger id, String author, Date date, String description, String mergedFrom) { // Constructeur
        super(id, author, date, description);
        this.mergedFrom = mergedFrom;
    }

    /**
     * @return la chaine de caratères des caractéristiques du commit
     */
    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                "mergedFrom...='" + mergedFrom + '\''+
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                (description != null ? (", description='" + description + '\'') : "" )+ //the other optional field
                '}';
    }
}
