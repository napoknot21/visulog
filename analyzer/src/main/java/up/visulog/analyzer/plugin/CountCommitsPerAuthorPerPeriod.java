package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CountCommitsPerAuthorPerPeriod implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerAuthorPerPeriod(Configuration generalConfiguration) {
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
        DateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date beginning, ending;
        String[] period = configuration.getPluginConfig(getClassConventionName()).period;
        try {
            if (period[0] == null || period[1] == null) throw new ParseException("", 0);
            String begin = configuration.getPluginConfig(getClassConventionName()).period[0];
            String end = configuration.getPluginConfig(getClassConventionName()).period[1];
            beginning = inputFormat.parse(begin); //inclus
            ending = inputFormat.parse(end); //non inclus
            result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()), beginning, ending);
        }catch(ParseException e){
            beginning = new Date(System.currentTimeMillis());
            ending = new Date(System.currentTimeMillis());
            result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()), beginning, ending);
        }
    }

    private String getClassConventionName() {
        String s = getClass().getSimpleName();
        return s.substring(0,1).toLowerCase() + s.substring(1);
    }


    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String , Integer> commitsPerPeriodPerAuthor = new HashMap<>();
        protected int nbCommitsPerPeriod = 0;
        @SuppressWarnings("all")
        public Map<String, Integer> getResultAsMap() {
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
            html.append("Nombre total de commits sur la periode : ").append(nbCommitsPerPeriod)
                    .append("</ul></div>");
            return html.toString();
        }
    }
}
