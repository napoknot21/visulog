package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import javax.swing.text.html.ObjectView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CountCommitsPerAuthorPerPeriod implements AnalyzerPlugin {

    private final Configuration configuration;
    private Result result;

    /**
     * Constructeur pour CountCommitsPerAuthorPerPeriod à partir d'une configuration
     * @param generalConfiguration Une configuration
     */
    public CountCommitsPerAuthorPerPeriod(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * Associe les auteurs dans une HasMap (result) avec leurs nombres de commits (il les rajoute s'ils n'y sont pas)
     * @param gitLog Liste de Commits
     * @param beginning Date du début
     * @param end Date de la fin
     * @return La HashMap qui associe les authors et leurs nombres de commits
     */
    static Result processLog(List<Commit> gitLog, Date beginning, Date end) {
        var result = new Result();
        for (var commit : gitLog){
            if (commit.date.after(beginning) && commit.date.before(end)){
                result.nbCommitsPerPeriod++;
                /*Cherche dans result si "commit.author" est déjà associé à un nb de commit
                si c'est le cas renvoie le nb de commit
                sinon renvoie 0 */
                var nb = result.commitsPerPeriodPerAuthor.getOrDefault(commit.author, 0);
                /* met à jour le nb de commit avec put (remplace la valeur précédente associée à la clé)
                /* si la clé y est déjà  */
                result.commitsPerPeriodPerAuthor.put(commit.author, nb + 1);
            }
        }
        return result;
    }

    /**
     * Initialise l'attribut result si ce dernier est null
     */
    @Override
    public void run() {
        DateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date beginning, ending;
        String[] period = configuration.getPluginConfig(getClass().getSimpleName()).period;
        try {
            if (period[0] == null || period[1] == null) throw new ParseException("", 0);
            String begin = configuration.getPluginConfig(getClass().getSimpleName()).period[0];
            String end = configuration.getPluginConfig(getClass().getSimpleName()).period[1];
            beginning = inputFormat.parse(begin); //inclus
            ending = inputFormat.parse(end); //non inclus
            result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()), beginning, ending);
        }catch(ParseException e){
            beginning = new Date(System.currentTimeMillis());
            ending = new Date(System.currentTimeMillis());
            result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()), beginning, ending);
        }
    }


    /**
     * Getter pour Result
     * @return L'attribut result
     */
    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    /**
     * Classe interne Result
     */
    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String , Integer> commitsPerPeriodPerAuthor = new HashMap<>();
        protected int nbCommitsPerPeriod = 0;

        /**
         * Getter pour Result en tant que Map
         * @return Une HashMap (Result)
         */
        public Map<String, Object> getResultAsMap() {
            return new HashMap<>(commitsPerPeriodPerAuthor);
        }

        /**
         * Over ride de toString()
         * @return La HashMap commitsPerPeriodPerAuthor en String
         */
        @Override
        public String getResultAsString() {
            return commitsPerPeriodPerAuthor.toString();
        }

        /**
         * Over ride de toHtml()
         * @return la HashMap commitsPerPeriodPerAuthor en tant que format html
         */
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
