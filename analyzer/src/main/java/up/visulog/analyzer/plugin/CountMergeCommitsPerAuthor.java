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

    /**
     * Constructeur de CountMergeCommmitPerAuthor à partir d'une configuration
     * @param generalConfiguration Une configuration
     */
    public CountMergeCommitsPerAuthor(Configuration generalConfiguration) {
        this.configuration=generalConfiguration;
    }

    /**
     * Cherche dans result si "commit.author" est déjà associé à un nb de commit
     * @param gitLog liste de commits
     * @return Le résultat
     */
    public static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            if(commit instanceof MergeCommit) {
                var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);
                /* met à jour le nb de commit avec put (remplace la valeur précédente associée à la clé)
                 * si la clé y est déjà  */
                result.commitsPerAuthor.put(commit.author, nb + 1);
            }
        }
        return result;
    }

    /**
     * On set la valeur de résultat
     */
    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    /**
     * @return Le résultat
     */
    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    /**
     * Classe interne statique Result
     */
    public static class Result implements AnalyzerPlugin.Result {
        protected final Map<String, Integer> commitsPerAuthor = new HashMap<>();
        public Map<String, Object> getResultAsMap() {
            return new HashMap<>(commitsPerAuthor);
        }

        /**
         * @return Result en tant que String
         */
        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        /**
         * @return Result en tant que format html
         */
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

