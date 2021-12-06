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
        String[] args = {"git", "log", "--numstat", commit.id};
        return parseDiff(ChangesDescription.processCommand(args , gitPath), commit);
    }

    public static LineChanges parseDiff(BufferedReader reader, Commit commit){
        try {
            int line_added = 0;
            int line_deleted = 0;
            String line = reader.readLine();
            String[] isMerged = line.split("\\s+");
            if (isMerged[0].equals("Merge:")) return new LineChanges(0,0,commit);
            while (!line.isEmpty()) line = reader.readLine();
            line = reader.readLine();
            while (!line.isEmpty()) line = reader.readLine();
            line = reader.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    String[] lineChanged = line.split("\\s+");
                    if (lineChanged[0].equals("-")) line_added += 0;
                    else line_added += Integer.valueOf(lineChanged[0]);
                    if (lineChanged[1].equals("-")) line_deleted += 0;
                    else line_deleted += Integer.valueOf(lineChanged[1]);
                    line = reader.readLine();
                }else{
                    reader.close();
                    break;
                }
            }
            return new LineChanges(line_added, line_deleted,commit);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
