package up.visulog.analyzer.plugin;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import java.util.*;

public class CountCommitsPerWeek implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerWeek(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog){
            Calendar calendar = Calendar.getInstance(Locale.FRANCE);
            calendar.setTime(commit.date);
            int year = calendar.get(calendar.YEAR);
            int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
            String keyDate = weekNumber + "_" + year;
            var nb = result.commitsPerWeek.getOrDefault(keyDate, 0);
            result.commitsPerWeek.put(keyDate, nb + 1);
        }
        return result;
    }

    public static HashMap<String, Integer> trierHashMap(HashMap<String, Integer> map, int i){
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                String[] key1 = o1.getKey().split("_");
                String[] key2 = o2.getKey().split("_");
                if  (key1[i] == key2[i]) return 0;
                if  (Integer.valueOf(key1[i]) > Integer.valueOf(key2[i])) return 1;
                else return -1;
            }
        });
        HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put( entry.getKey(), entry.getValue() );
        }
        return sortedMap;
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
        protected final HashMap<String , Integer> commitsPerWeek = new HashMap<>();
        public Map<String, Object> getResultAsMap() {
            return new HashMap<>(commitsPerWeek);
        }

        @Override
        public String getResultAsString() {
            return commitsPerWeek.toString();
        }


        public String getResultAsHtmlDiv() {
            HashMap<String, HashMap<String, Integer>> annees = new HashMap<>();
            for (var item : commitsPerWeek.entrySet()){
                String[] date = item.getKey().split("_");
                if (!annees.containsKey(date[1])){
                    HashMap<String, Integer> commitsAnnee = new HashMap<>();
                    annees.put(date[1], commitsAnnee);
                }annees.get(date[1]).put(date[0], item.getValue());
            }
            System.out.println();
            StringBuilder html = new StringBuilder("<div>Commits per period : <ul>");
            for (var annee : annees.entrySet()) {
                html.append(annee.getKey());
                for (var item : annee.getValue().entrySet()) {
                    html.append("<li>")
                            .append(item.getKey())
                            .append(": ")
                            .append(item.getValue())
                            .append("</ul>")
                            .append("</li>");
                }
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}

