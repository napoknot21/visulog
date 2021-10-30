package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MergeCommit extends Commit{
    public final String mergedFrom; // la branche d'où vient la modification

    public MergeCommit(String id, String author, Date date, String description, String mergedFrom) { // simplement le constructeur
        super(id, author, date, description);
        this.mergedFrom = mergedFrom;
    }

    @Override
    public String toString() { // la méthode pour afficher les caractéristiques d'un commit
        return "Commit{" +
                "id='" + id + '\'' +
                (mergedFrom != null ? ("mergedFrom...='" + mergedFrom + '\'') : "") +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                (description != null ? (", description='" + description + '\'') : "" )+ //the other optional field
                '}';
    }
}
