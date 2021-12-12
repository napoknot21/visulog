package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.LineChanges;
import java.util.HashMap;
import java.util.Map;

public class CountLinesPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountLinesPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result process(Configuration config) {
        var result = new CountLinesPerAuthorPlugin.Result(); /* Crée un HashMap qui va associer commit(key) à nb de lignes changées(value) */
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

    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String, int[]> lineChangesPerAuthor = new HashMap<>();

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
        public Map<String, Integer> getResultAsMap() {
            return null;
        }
    }
}
