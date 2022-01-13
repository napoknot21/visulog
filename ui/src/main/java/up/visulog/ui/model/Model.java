package up.visulog.ui.model;

import up.visulog.analyzer.AnalyzerResult;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Model implements MapRelations {

    private AnalyzerResult result;
    private String currentPlugin = "";


    public void update(AnalyzerResult result, String plugin) {
        this.result = result;
        this.currentPlugin = plugin;

    }

    public boolean isPluginLaunched() {
        return this.result != null;
    }

    public String getCurrentPlugin() {
        return currentPlugin;
    }

    public String toHtml() { // retourne le resultats sous html
        if (result == null) return "";
        System.out.println(result.toHTML());
        return result.toHTML();
    }

    /**
     * Recupere les resultats du plugin sous forme de liste de map
     * @return la liste de tous les resultats sous forme de map simple '\<'String, Integer'\>'
     */
    public List<Map<String, Integer>> getResultAsMap() {
        return selector(result.toMap());
    }

    private <C> List<Map<String, Integer>> selector(Map<String, C> data) {
        C obj = getObjectFormMap(data);
        if (obj instanceof Integer) return integerTreatment(data, obj);
        if (obj instanceof int[]) return intArrayTreatment(data, obj);
        return null;
    }

    private <C> List<Map<String, Integer>> intArrayTreatment(Map<String, C> data, C obj) {
        if (!(obj instanceof int[])) return null;
        int[] t = (int[]) obj;
        List<Map<String, Integer>> list = new LinkedList<>();
        for (int i = 0; i < t.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            for (var key : data.keySet()) {
                int[] val = (int[]) data.get(key);
                map.put(key, val[i]);
            }
            list.add(map);
        }
        return list;
    }


    private <C> List<Map<String, Integer>> integerTreatment(Map<String, C> data, C obj) {
        if (!(obj instanceof Integer)) return null;
        List<Map<String, Integer>> list = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();
        data.forEach((key, value) -> map.put(key, (int) value));
        list.add(map);
        return list;
    }

    private <C> C getObjectFormMap(Map<String, C> data) {
        for (var v : data.values()) {
            return v;
        }
        return null;
    }

    public AnalyzerResult getResult() {
        return result;
    }

}
