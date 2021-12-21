package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import java.util.*;

public class CountCommitsPerWeekPlugin implements AnalyzerPlugin{
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerWeekPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog){
            Calendar calendar = Calendar.getInstance(Locale.FRANCE);
            calendar.setTime(commit.date);
            int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
            var nb = result.commitsPerWeek.getOrDefault(Integer.toString(weekNumber), 0);
            result.commitsPerWeek.put(Integer.toString(weekNumber), nb + 1);
        }
        return result;
    }

    public static HashMap<String, Integer> trierHashMap(HashMap<String, Integer> map){
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if  (Integer.valueOf(o1.getKey()) == Integer.valueOf(o2.getKey())) return 0;
                if  (Integer.valueOf(o1.getKey()) > Integer.valueOf(o2.getKey())) return 1;
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
        public Map<String , Integer> getResultAsMap() {
            return commitsPerWeek;
        }

        @Override
        public String getResultAsString() {
            return commitsPerWeek.toString();
        }


        public String getResultAsHtmlDiv() {
            HashMap<String, Integer> sortedMap = trierHashMap(commitsPerWeek);
            StringBuilder html = new StringBuilder("<div>Commits per period : <ul>");
            for (var item : sortedMap.entrySet()) {
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

