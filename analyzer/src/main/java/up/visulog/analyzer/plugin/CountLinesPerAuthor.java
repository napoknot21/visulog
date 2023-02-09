package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.LineChanges;
import java.util.HashMap;
import java.util.Map;

public class CountLinesPerAuthor implements AnalyzerPlugin {

    private final Configuration configuration;
    private Result result;

    /**
     * Constructeur pour CountLinesPerAuthor à partir d'une configuration
     * @param generalConfiguration Une configuration
     */
    public CountLinesPerAuthor(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
     * Crée une HashMap qui va associer un commit à un nombre de lignes
     * @param config Une configuration
     * @return La HashMap (Result)
     */
    static Result process(Configuration config) {
        var result = new CountLinesPerAuthor.Result();
        result.getLineChangesPerAuthor().putAll(LineChanges.parseDiffFromCommand(config.getGitPath()));
        return result;
    }

    @Override
    public void run() {
        result = process(configuration);
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    /**
     * Classe interne Result
     */
    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String, int[]> lineChangesPerAuthor = new HashMap<>();

        /**
         * Getter pour lineChangesPerAuthor de la classe interne Result
         * @return la map lineChangesPerAuthor
         */
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

        @Override
        public Map<String, Object> getResultAsMap() {
            return new HashMap<>(lineChangesPerAuthor);
        }
    }
}
