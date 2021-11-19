package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.CharacterChanges;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCharactersPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCharactersPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result process(List<Commit> gitLog, Configuration config) {
        var result = new CountCharactersPerAuthorPlugin.Result(); /* Crée un HashMap qui va associer commit(key) à nb de caractères changés(value) */

        /*Parcours les commits*/
        for (var commit : gitLog) {
            CharacterChanges change = CharacterChanges.parseDiffFromCommand(config.getGitPath(), commit);
            int[] changes = new int[2];
            /*Cherche dans result si "commit.author" est déjà associé à un nb de caractères changés:
            si c'est le cas renvoie le un tableau contenant les nb de caractères changés
            sinon renvoie un tableau vide */
            changes = result.characterChangesPerAuthor.getOrDefault(commit.author, changes);
            changes[0] += change.addedCharacters;
            changes[1] -= change.removedCharacters;
            /* met à jour le tableau contenant les nb de caractères changés avec put (remplace la valeur précédente associée à la clé)
             * si la clé y est déjà  */
            result.characterChangesPerAuthor.put(commit.author, changes);
        }
        return result;
    }

    @Override
    public void run() {
        result = process(Commit.parseLogFromCommand(configuration.getGitPath()), configuration);
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }


    static class Result implements AnalyzerPlugin.Result {
        protected final Map<String, int[]> characterChangesPerAuthor = new HashMap<>();

        Map<String, int[]> getCharacterChangesPerAuthor() {
            return characterChangesPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return characterChangesPerAuthor.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Character changes per author: <ul>");
            for (var item : characterChangesPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()[0]).append("</li>").append(item.getValue()[1]).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
