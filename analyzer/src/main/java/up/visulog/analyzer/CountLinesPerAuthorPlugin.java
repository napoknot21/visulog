package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.LineChanges;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountLinesPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountLinesPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result process(List<Commit> gitLog, Configuration config) {
        var result = new CountLinesPerAuthorPlugin.Result(); /* Crée un HashMap qui va associer commit(key) à nb de lignes changées(value) */

        /*Parcours les commits*/
        for (var commit : gitLog) {
            LineChanges change = LineChanges.parseDiffFromCommand(config.getGitPath(), commit);
            int[] changes = new int[2];
            /*Cherche dans result si "commit.author" est déjà associé à un nb de lignes changées:
            si c'est le cas renvoie le un tableau contenant les nb de lignes changées
            sinon renvoie un tableau vide */
            changes = result.lineChangesPerAuthor.getOrDefault(commit.author, changes);
            changes[0] += change.addedLines;
            changes[1] -= change.removedLines;
            /* met à jour le tableau contenant les nb de lignes changées avec put (remplace la valeur précédente associée à la clé)
             * si la clé y est déjà  */
            result.lineChangesPerAuthor.put(commit.author, changes);
        }
        return result;
    }

    @Override
    public void run() {
        result = process(Commit.parseLogFromCommand(configuration.getGitPath()), configuration);
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }


    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String, int[]> lineChangesPerAuthor = new HashMap<>();

        Map<String, int[]> getLineChangesPerAuthor() {
            return lineChangesPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return lineChangesPerAuthor.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Line changes per author: <ul>");
            for (var item : lineChangesPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()[0]).append("</li>").append(item.getValue()[1]).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
