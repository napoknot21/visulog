package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CountCommitsPerAuthorPerDayPlugin implements  AnalyzerPlugin{
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerAuthorPerDayPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    public static ArrayList<String> getAuthors(List<Commit> gitLog){
        List<String> authors = new ArrayList<>();
        Map<String, Boolean> present = new HashMap<>();
        for(var commit:gitLog){
            present.put(commit.author,true);
        }
        for(var auth : present.entrySet()){
            authors.add(auth.getKey());
        }
        return (ArrayList<String>) authors;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        ArrayList<String> commitsPerAuthor = getAuthors(gitLog);

        for(String author : commitsPerAuthor){ /*On parcourt d'abord tous les auteurs*/
            Map<String, Integer> commitsPerDay = new HashMap<>();
            for (var commit : gitLog){
                /*On s'intéresse qu'aux commits d'une certaine personne*/
                if(commit.author.equals(author)){

                    /*On change le format de la date pour que l'on puisse trier par date*/
                    DateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy");
                    String dateString = outputFormat.format(commit.date);

                    /*On compte les commits de l'auteur par date*/
                    var nb = commitsPerDay.getOrDefault(dateString, 0);
                    commitsPerDay.put(dateString, nb+1);

                }
            }
            result.commitsPerAuthorPerDay.put(author, commitsPerDay);

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
        protected final Map<String , Map<String, Integer>> commitsPerAuthorPerDay = new HashMap<>();
        Map<String , Map<String, Integer>> getCommitsPerAuthorPerDay() {
            return commitsPerAuthorPerDay;
        }

        @Override
        public String getResultAsString() {
            return commitsPerAuthorPerDay.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per author per day: <ul>");
            for (var item : commitsPerAuthorPerDay.entrySet()) {
                html.append("<li>")
                        .append(item.getKey())
                        .append(": ")
                        .append("<ul>");
                for (var commit : commitsPerAuthorPerDay.get(item.getKey()).entrySet()){
                    html.append("<li>")
                            .append(commit.getKey())
                            .append(": ")
                            .append(commit.getValue())
                            .append("</li>");
                }
                html.append("</ul>")
                    .append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}

