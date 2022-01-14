package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LineChanges {

    public static String author;

    public static Map<String, int[]> parseDiffFromCommand(Path gitPath) {
        String[] args = {"git", "log", "--numstat"};
        return parseDiff(ChangesDescription.processCommand(args, gitPath));
    }

    /**
     * Calcule le nombre de lignes qui ont été ajoutées/retirées par auteur
     * @param reader représente le contenu dans le log de git
     * @return une map qui montre les différences de lignes ajoutées/retirées par auteur
     */
    public static Map<String, int[]> parseDiff(BufferedReader reader) {
        var objects = reader.lines().toArray();
        HashMap<String, int[]> map = new HashMap<>();
        for (var obj : objects) {
            if (obj instanceof String) {
                boolean addChanged = false;
                boolean rmChanged = false;
                boolean nameChanged = false;
                String line = (String) obj;
                int addedLine = 0;
                int removedLine = 0;
                if (!line.isBlank()) {
                    String[] lineChanged = line.split("\\s+");
                    String title = lineChanged[0];
                    if (title.equals("Author:")) {
                        StringBuilder stringBuilder = new StringBuilder();
                        boolean stop = false;
                        for (int i = 1; i < lineChanged.length && !stop; i++) {
                            if (lineChanged[i].contains("<")) stop = true;
                            else stringBuilder.append(lineChanged[i]).append(" ");
                        }
                        author = stringBuilder.toString();
                        nameChanged = true;
                    }
                    if (lineChanged[0].matches("\\d+")) {
                        addedLine += Integer.parseInt(lineChanged[0]);
                        addChanged = true;
                    }
                    if (lineChanged[1].matches("\\d+")) {
                        removedLine += Integer.parseInt(lineChanged[1]);
                        rmChanged = true;
                    }
                    if (addChanged && rmChanged || nameChanged) {
                        int[] val = map.getOrDefault(author, new int[2]);
                        val[0] += addedLine;
                        val[1] += removedLine;
                        map.put(author, val);
                    }
                }
            }
        }
        return map;
    }


}
