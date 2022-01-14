package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.MergeCommit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountMergeCommitsPerAuthor implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountMergeCommitsPerAuthor(Configuration generalConfiguration) {
        this.configuration=generalConfiguration;
    }

    public static Result processLog(List<Commit> gitLog) {
        var result = new Result();


        for (var commit : gitLog) {
            /*Cherche dans result si "commit.author" est déjà associé à un nb de commit:
            si c'est le cas renvoie le nb de commit
            sinon renvoie 0 */
            if(commit instanceof MergeCommit) {
                var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);

                /* met à jour le nb de commit avec put (remplace la valeur précédente associée à la clé)
                 * si la clé y est déjà  */
                result.commitsPerAuthor.put(commit.author, nb + 1);
            }
        }
        return result;
    }

    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    public static class Result implements AnalyzerPlugin.Result {
        protected final Map<String, Integer> commitsPerAuthor = new HashMap<>();
        public Map<String, Object> getResultAsMap() {
            return new HashMap<>(commitsPerAuthor);
        }

        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Merge commits per author: <ul>");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}

