package up.visulog.gitrawdata;

import java.math.BigInteger;
import java.util.Date;

public class MergeCommit extends Commit{

    public final String mergedFrom;

    /**
     * Constructeur de MergeCommit
     * @param id L'identifiant en hexadecimal d'après API
     * @param author Le nom du collaborateur qui a commit
     * @param date Le moment auquel le commit a été réalisé
     * @param description Une brève description de la modification qui a été faite
     * @param mergedFrom La branche d'où vient la modification
     */
    public MergeCommit(BigInteger id, String author, Date date, String description, String mergedFrom) { // Constructeur
        super(id, author, date, description);
        this.mergedFrom = mergedFrom;
    }

    /**
     * @Override de toString()
     * @return la chaine de caractères des caractéristiques du commit
     */
    @Override
    public String toString() { // la méthode pour afficher les caractéristiques d'un commit
        return "Commit{" +
                "id='" + id + '\'' +
                "mergedFrom...='" + mergedFrom + '\''+
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                (description != null ? (", description='" + description + '\'') : "" )+ //the other optional field
                '}';
    }
}
