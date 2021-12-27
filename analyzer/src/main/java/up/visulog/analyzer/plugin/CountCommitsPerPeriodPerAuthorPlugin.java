package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitsPerPeriodPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerPeriodPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog, Date beginning, Date end) {
        var result = new Result();
        for (var commit : gitLog){
            if (commit.date.after(beginning) && commit.date.before(end)){
                result.nbCommitsPerPeriod++;
                /*Cherche dans result si "commit.author" est déjà associé à un nb de commit:
            si c'est le cas renvoie le nb de commit
            sinon renvoie 0 */
                var nb = result.commitsPerPeriodPerAuthor.getOrDefault(commit.author, 0);

                /* met à jour le nb de commit avec put (remplace la valeur précédente associée à la clé)
                 * si la clé y est déjà  */
                result.commitsPerPeriodPerAuthor.put(commit.author, nb + 1);
            }
        }
        return result;
    }

    @Override
    public void run() {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date beginning = inputFormat.parse("2021-11-10"); //inclus
            Date end = inputFormat.parse("2021-11-21"); //non inclus
            result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()), beginning, end);
        }catch(ParseException e){
            System.out.println("bug");
            result = null;
        }
    }


    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String , Integer> commitsPerPeriodPerAuthor = new HashMap<>();
        protected int nbCommitsPerPeriod = 0;
        public Map<String , Integer> getResultAsMap() {
            return commitsPerPeriodPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return commitsPerPeriodPerAuthor.toString();
        }


        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per period : <ul>");
            for (var item : commitsPerPeriodPerAuthor.entrySet()) {
                html.append("<li>")
                        .append(item.getKey())
                        .append(": ")
                        .append(item.getValue())
                        .append("</ul>")
                        .append("</li>");
            }
            html.append("Nombre total de commits sur la période : " + nbCommitsPerPeriod)
                    .append("</ul></div>");
            return html.toString();
        }
    }
}
