package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;


import java.text.SimpleDateFormat;
import java.util.*;

public class ResearchPlugin implements AnalyzerPlugin
{
    private final Configuration configuration;
    private ArrayList<String> keywords;
    private Result result;

    public ResearchPlugin(Configuration generalConfiguration){
        this.configuration = generalConfiguration;
        keywords = configuration.getPluginConfig("research").get("keyWords");
    }

    static Result processLog(List<Commit> gitLog, ArrayList<String> keyWords) {
        var result = new ResearchPlugin.Result();
        for (var commit : gitLog){
            boolean init = true;
            for (String key : keyWords){
                if(!findKeyWords(commit, key)) init = false;
            }
            if (init)
                result.commitsFound.put(commit.id,commit);
        }
        return result;
    }


    public static boolean findKeyWords(Commit commit, String keyWord){
        if (commit.author.indexOf(keyWord) != -1 ){
            return true;
        }
        if (commit.id.indexOf(keyWord) != -1 ){
            return true;
        }
        if (commit.description.indexOf(keyWord) != -1 ){
            return true;
        }
        String dateString = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z", Locale.ENGLISH).format(commit.date);
        if(dateString.indexOf(keyWord) != -1){
            return true;
        }
        for (String files : commit.files){ //FIXME : à modifier quand on ajouter la liste des fichiers modifiés dans commitBuilder
            if(files.indexOf(keyWord) != -1) return true;
        }
        return false;
    }


    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()), keywords);

    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String, Commit> commitsFound = new HashMap<>();
        Map<String,Commit> getCommitsFound() {
            return  commitsFound;
        }

        @Override
        public String getResultAsString() {
            return commitsFound.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits related to the keyword : <ul>");
            for (var item : commitsFound.entrySet()) {
                html.append("<li>").append(item.getValue().toString()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
