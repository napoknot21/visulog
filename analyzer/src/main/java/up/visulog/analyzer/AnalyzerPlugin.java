package up.visulog.analyzer;

import java.util.Map;

public interface AnalyzerPlugin extends Runnable {
    /**
     * run this analyzer plugin
     */
    void run();

    /**
     * @return the result of this analysis. Runs the analysis first if not already done.
     */
    Result getResult();

    interface Result {
        String getResultAsString();

        String getResultAsHtmlDiv();

        @SuppressWarnings("all")
        <C> Map<String, C> getResultAsMap();

    }
}
