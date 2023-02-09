package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitsPerAuthor implements AnalyzerPlugin {

    private final Configuration configuration;
    private Result result;

    /**
     * Constructeur de CountCommitsPerAuthor à partir d'une configuration
     * @param generalConfiguration La configuration
     */
    public CountCommitsPerAuthor(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * Associe les auteurs dans une HasMap (Result) avec leurs nombres de commits (il les rajoute s'ils n'y sont pas)
     * @param gitLog Liste de commits
     * @return
     */
    public static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            /*Cherche dans result si "commit.author" est déjà associé à un nb de commit:
            si c'est le cas renvoie le nb de commit
            sinon renvoie 0 */
            var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);

            /* met à jour le nb de commit avec put (remplace la valeur précédente associée à la clé)
             * si la clé y est déjà  */
            result.commitsPerAuthor.put(commit.author, nb + 1);
        }
        return result;
    }

    /**
     * Initialise result s'il est null
     */
    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    /**
     * Getter pour résultat
     * @return Le résultat de CountCommitsPerAuthor
     */
    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    /**
     * Classe statique interne Result
     */
    public static class Result implements AnalyzerPlugin.Result {

        protected final Map<String, Integer> commitsPerAuthor = new HashMap<>(); //FIXME : protected ou private ?

        /**
         * Getter pour Result en tant que Map
         * @return Une HashMap (Result)
         */
        @Override
        public Map<String, Object> getResultAsMap() {
            return new HashMap<>(commitsPerAuthor);
        }

        /**
         * Over ride de toString()
         * @return La HashMap commitsPerAuthor en String
         */
        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        /**
         * Over ride de toHtml()
         * @return la HashMap commitsPerAuthor en tant que format html
         */
        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div><h1>Commits per author:</h1> <ul>");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
