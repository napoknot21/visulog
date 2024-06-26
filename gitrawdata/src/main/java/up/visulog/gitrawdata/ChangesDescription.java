package up.visulog.gitrawdata;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Date;

public class ChangesDescription { //objets qui définissent un changement apporté à ub fichier (commits, mergecommits, ...)
    public final BigInteger id; // id est en hexadecimal d'après API
    public final Date date; // le moment auquel le commit a été réalisé
    public final String author; // le nom du collaborateur qui a commit
    public final String description; // une brève description de la modofication qui a été faite

    /**
     * Constructeur de ChangesDescription
     * @param id L'identifiant en hexadecimal d'après API
     * @param author Le nom du collaborateur qui a commit
     * @param date Le moment auquel le commit a été réalisé
     * @param description Une brève description de la modification qui a été faite
     */
    public ChangesDescription(BigInteger id, String author, Date date, String description) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.description = description;
    }

    public static BufferedReader processCommand (String[] args, Path gitPath) {
        ProcessBuilder builder = new ProcessBuilder(args).directory(gitPath.toFile());
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Error running\"" +args[0]+" "+args[2]+"\".", e);
        }
        InputStream is = process.getInputStream();
        return new BufferedReader(new InputStreamReader(is));
    }

}