package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.util.Date;

public class CharacterChanges extends ChangesDescription{
    public final int addedCharacters;
    public final int removedCharcaters;

    public CharacterChanges(String id, String author, Date date, String description, int added, int removed) { // simplement le constructeur
        super(id, author, date, description);
        this.addedCharacters = added;
        this.removedCharcaters = removed;
    }

    public static void/*CharacterChanges*/ parseDiffFromCommand (Path gitPath, String idCommit) {
        //return parseDiff(ChangesDescription.processCommand ("git","diff -numstat " + idCommit,gitPath));
        parseDiff(ChangesDescription.processCommand ("git","diff -numstat " + idCommit,gitPath));
    }

    public static void/*CharacterChanges*/ parseDiff(BufferedReader reader){
        System.out.println("reader");
    }
}
