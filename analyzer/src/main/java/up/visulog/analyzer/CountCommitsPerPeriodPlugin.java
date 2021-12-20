package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CountCommitsPerPeriodPlugin implements  AnalyzerPlugin{
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerPeriodPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog, Date beginning, Date end) {
        var result = new Result();
        int nbCommits = 0;
        for (var commit : gitLog){
            if (commit.date.after(beginning) && commit.date.before(end)){
                nbCommits++;
            }
        }
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = outputFormat.format(beginning);
        result.commitsPerPeriod.put(dateString, nbCommits);
        return result;
    }

    @Override
    public void run() {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date beginning = inputFormat.parse("2021-11-10");
            Date end = inputFormat.parse("2021-11-21");
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
        protected final Map<String , Integer> commitsPerPeriod = new HashMap<>();
        public Map<String , Integer> getResultAsMap() {
            return commitsPerPeriod;
        }

        @Override
        public String getResultAsString() {
            return commitsPerPeriod.toString();
        }


        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per period : <ul>");
            for (var item : commitsPerPeriod.entrySet()) {
                html.append("<li>")
                        .append(item.getKey())
                        .append(": ")
                        .append(item.getValue())
                        .append("</ul>")
                        .append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
