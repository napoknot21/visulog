package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitsPerAuthorPerDayPlugin implements  AnalyzerPlugin{
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerAuthorPerDayPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();

        for (var commit : gitLog) {

            var nb = result.commitsPerDay.getOrDefault(commit.date, 0);

            /* met à jour le nb de commit avec put (remplace la valeur précédente associée à la clé)
             * si la clé y est déjà  */
            result.commitsPerDay.put(commit.date, nb + 1);
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
        protected final Map<Date, Integer> commitsPerDay = new HashMap<>(); //FIXME : protected ou private ?

        Map<Date, Integer> getCommitsPerAuthor() {
            return commitsPerDay;
        }

        @Override
        public String getResultAsString() {
            return commitsPerDay.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per day: <ul>");
            for (var item : commitsPerDay.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}

