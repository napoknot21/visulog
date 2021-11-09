package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.util.Date;String id, String author, Date date, String description,
import java.io.IOException;
import java.util.Optional;

public class CharacterChanges extends ChangesDescription{
    public final int addedCharacters;
    public final int removedCharcaters;
    public Commit commit;

    public CharacterChanges(int added, int removed, Commit commit) { // simplement le constructeur
        super(commit.id, commit.author, commit.date, commit.description);
        this.addedCharacters = added;
        this.removedCharcaters = removed;
        this.commit = commit;
    }

    public static CharacterChanges parseDiffFromCommand (Path gitPath, Commit commit) {
        return parseDiff(ChangesDescription.processCommand ("git","diff --numstat " + commit.id,gitPath), commit);
    }

    public static CharacterChanges parseDiff(BufferedReader reader, Commit commit){
        String line = null;
        try {
            line = reader.readLine();

            int char_added = 0;
            int char_deleted = 0;
            while (line != null) {
                String[] charChanged = line.split(" ");
                char_added += Integer.valueOf(charChanged[0]);
                char_deleted += Integer.valueOf(charChanged[1]);
            }
            CharacterChanges cc = new CharacterChanges(char_added,char_deleted,commit);
            return cc;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
