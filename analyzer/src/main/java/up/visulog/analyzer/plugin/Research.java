package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Cette classe modélise la fonctionnalité de recherche de commits à partir de mots clés
 */
public class Research implements AnalyzerPlugin {
    
    private final Configuration configuration;
    private List<String> keywords;
    private Result result;

    /**
     * Constructeur de Research à partir d'une Configuration
     * @param generalConfiguration Configuration générale
     */
    public Research(Configuration generalConfiguration){
        this.configuration = generalConfiguration;
        keywords = configuration.getPluginConfig("research").get("keyWords");
    }

    /**
     * @param gitLog la liste exhaustive des commits
     * @param keyWords le/les mot(s) clé(s)
     * @return la liste des commits où figure le/les mot(s) clé(s)
     */
    static Result processLog(List<Commit> gitLog, List<String> keyWords) {
        var result = new Research.Result();
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
        if (compare(commit.id.toString(),keyWord)){
            return true;
        }
        if (compare(commit.description,keyWord)){
            return true;
        }
        String dateString = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z", Locale.ENGLISH).format(commit.date);
        if(compare(dateString,keyWord)){
            return true;
        }
        for (String files : commit.files){ //FIXME : à modifier quand on ajoutera la liste des fichiers modifiés dans commitBuilder
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

    /**
     * @return Le result
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

        protected final Map<BigInteger, Commit> commitsFound = new HashMap<>();

        /**
         * Getter pour commitsFound de Result
         * @return la Hap commitFound
         */
        Map<BigInteger,Commit> getCommitsFound() {
            return  commitsFound;
        }

        /**
         * Getter pour commitsFound en tant que String
         * @return la Map commitFound en tant que String
         */
        @Override
        public String getResultAsString() {
            return commitsFound.toString();
        }

        /**
         * Getter pour commitFound en tant que format html
         * @return la Map commitFound en tant que format html
         */
        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits related to the keyword : <ul>");
            for (var item : commitsFound.entrySet()) {
                html.append("<li>").append(item.getValue().toString()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }

        /**
         * La liste des commits en tant que Map
         * @return les commits en tant que Map
         */
        @Override
        public Map<String, Object> getResultAsMap() {
            List <Commit> commits = new ArrayList<>();
            for(var c : commitsFound.entrySet()){
                commits.add(c.getValue());
            }
            var result = CountCommitsPerAuthor.processLog(commits);
            return new HashMap<>(result.getResultAsMap());
        }
    }
}
