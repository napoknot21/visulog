package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.io.IOException;

public class LineChanges extends ChangesDescription{
    public final int addedLines;
    public final int removedLines;
    public Commit commit;

    public LineChanges(int added, int removed, Commit commit) { // simplement le constructeur
        super(commit.id, commit.author, commit.date, commit.description);
        this.addedLines = added;
        this.removedLines = removed;
        this.commit = commit;
    }

    public static LineChanges parseDiffFromCommand (Path gitPath, Commit commit) {
        return parseDiff(ChangesDescription.processCommand("git","diff --numstat " + commit.id, gitPath), commit);
    }

    public static LineChanges parseDiff(BufferedReader reader, Commit commit){
        try {
            String line = reader.readLine();
            int line_added = 0;
            int line_deleted = 0;
            while (line.isEmpty()) {
                String[] lineChanged = line.split(" ");
                if (lineChanged[0].equals("-")) line_added += 0;
                else line_added += Integer.valueOf(lineChanged[0]);
                if (lineChanged[1].equals("-")) line_deleted += 0;
                else line_deleted += Integer.valueOf(lineChanged[1]);
                line = reader.readLine();
            }
            LineChanges cc = new LineChanges(line_added, line_deleted,commit);
            return cc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
