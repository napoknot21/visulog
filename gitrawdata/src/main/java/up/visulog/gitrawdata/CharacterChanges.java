package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.io.IOException;

public class CharacterChanges extends ChangesDescription{
    public final int addedCharacters;
    public final int removedCharacters;
    public Commit commit;

    public CharacterChanges(int added, int removed, Commit commit) { // simplement le constructeur
        super(commit.id, commit.author, commit.date, commit.description);
        this.addedCharacters = added;
        this.removedCharacters = removed;
        this.commit = commit;
    }

    public static CharacterChanges parseDiffFromCommand (Path gitPath, Commit commit) {
        return parseDiff(ChangesDescription.processCommand("git","diff --numstat " + commit.id, gitPath), commit);
    }

    public static CharacterChanges parseDiff(BufferedReader reader, Commit commit){
        try {
            String line = reader.readLine();
            int char_added = 0;
            int char_deleted = 0;
            while (line.isEmpty()) {
                String[] charChanged = line.split(" ");
                if (charChanged[0].equals("-")) char_added += 0;
                else char_added += Integer.valueOf(charChanged[0]);
                if (charChanged[1].equals("-")) char_deleted += 0;
                else char_deleted += Integer.valueOf(charChanged[1]);
                line = reader.readLine();
            }
            CharacterChanges cc = new CharacterChanges(char_added,char_deleted,commit);
            return cc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
