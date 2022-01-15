package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import java.util.*;

public class CountCommitsPerWeek implements AnalyzerPlugin {

    private final Configuration configuration;
    private Result result;

    /**
     * Constructeur pour CountCommistPerWeek à partir d'une configuration
     * @param generalConfiguration Une configuration
     */
    public CountCommitsPerWeek(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * On ajoute les commits à la HasMap (Result) à une clé (String)
     * @param gitLog Liste de commits
     * @return Une HashMap (Result)
     */
    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            Calendar calendar = Calendar.getInstance(Locale.FRANCE);
            calendar.setTime(commit.date);
            int year = calendar.get(Calendar.YEAR);
            int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
            String keyDate = weekNumber + "_" + year;
            var nb = result.commitsPerWeek.getOrDefault(keyDate, 0);
            result.commitsPerWeek.put(keyDate, nb + 1);
        }
        return result;
    }

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
    static class Result implements AnalyzerPlugin.Result {
        protected final HashMap<String, Integer> commitsPerWeek = new HashMap<>();

        /**
         * Getter pour Result en tant que Map
         * @return Une HashMap (Result)
         */
        public Map<String, Object> getResultAsMap() {
            return new HashMap<>(commitsPerWeek);
        }

        @Override
        public String getResultAsString() {
            return commitsPerWeek.toString();
        }


        /**
         * @return Result en tant que format html
         */
        @Override
        public String getResultAsHtmlDiv() {
            HashMap<String, HashMap<String, Integer>> annees = new HashMap<>();
            for (var item : commitsPerWeek.entrySet()) {
                String[] date = item.getKey().split("_");
                if (!annees.containsKey(date[1])) {
                    HashMap<String, Integer> commitsAnnee = new HashMap<>();
                    annees.put(date[1], commitsAnnee);
                }
                annees.get(date[1]).put(date[0], item.getValue());
            }
            System.out.println();
            StringBuilder html = new StringBuilder("<div>Commits per period : <ul>");
            for (var annee : annees.entrySet()) {
                html.append(annee.getKey());
                for (var item : annee.getValue().entrySet()) {
                    html.append("<li>")
                            .append(item.getKey())
                            .append("eme semaine : ")
                            .append(item.getValue())
                            .append(" commits</ul>")
                            .append("</li>");
                }
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}

