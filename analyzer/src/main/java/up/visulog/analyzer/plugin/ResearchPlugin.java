package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Cette classe modélise la fonctionnalité de recherche de commits à partir de mots clés
 */
public class ResearchPlugin implements AnalyzerPlugin
{
    private final Configuration configuration;
    private ArrayList<String> keywords;
    private Result result;

    public ResearchPlugin(Configuration generalConfiguration){
        this.configuration = generalConfiguration;
        keywords = configuration.getPluginConfig("research").get("keyWords");
    }

    /**
     * @param gitLog la liste exhaustive des commits
     * @param keyWords le/les mot(s) clé(s)
     * @return la liste des commits où figure le/les mot(s) clé(s)
     */
    static Result processLog(List<Commit> gitLog, ArrayList<String> keyWords) {
        var result = new ResearchPlugin.Result();
        for (var commit : gitLog){
            boolean init = true;
            for (String key : keyWords){
                if(!findKeyWords(commit, key)) init = false;
            }
            if (init)
            /*le commit est enregistré ssi tous les mots clés y figurent*/
                result.commitsFound.put(commit.id,commit);
        }
        return result;
    }

    /**
     * Comparaison au niveau des mots clés et des caractéristiques du commits
     * @return vrai si le mot clé est mentionné dans au moins un des attributs d'un commit
     */
    public static boolean findKeyWords(Commit commit, String keyWord){
        if (compare(commit.author,keyWord)){
            return true;
        }
        if (compare(commit.id,keyWord)){
            return true;
        }
        if (compare(commit.description,keyWord)){
            return true;
        }
        String dateString = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z", Locale.ENGLISH).format(commit.date);
        if(compare(dateString,keyWord)){
            return true;
        }
        for (String files : commit.files){ //FIXME : à modifier quand on ajouter la liste des fichiers modifiés dans commitBuilder
            if(compare(files,keyWord)) return true;
        }
        return false;
    }

    /**
     * Comparaison au niveau des String
     * @return vrai si le String data contient le String key
     */
    public static boolean compare(String data, String key){
        return (data.toLowerCase().contains(key.toLowerCase()) );
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
