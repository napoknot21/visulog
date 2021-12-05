package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitsPerAuthorPlugin implements AnalyzerPlugin {
    protected final Configuration configuration;
    protected Result result;

    public CountCommitsPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result(); /* Crée un HashMap qui va associer auteur(key) à nb de commit(value) */

        /*Parcours les commits*/
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

    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String, Integer> commitsPerAuthor = new HashMap<>(); //FIXME : protected ou private ?

        public Map<String, Integer> getResultAsMap() {
            return commitsPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div><h1>Commits per author:</h1> <ul>");
            for (var item : commitsPerAuthor.entrySet()) {
                System.out.println(item);
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
