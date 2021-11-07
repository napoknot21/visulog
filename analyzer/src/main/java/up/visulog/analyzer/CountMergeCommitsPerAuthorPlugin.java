package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.MergeCommit;

import java.util.List;

public class CountMergeCommitsPerAuthorPlugin extends CountCommitsPerAuthorPlugin{


    public CountMergeCommitsPerAuthorPlugin(Configuration generalConfiguration) {
        super(generalConfiguration);
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result(); /* Crée un HashMap qui va associer auteur(key) à nb de commit(value) */

        /*Parcours les commits*/
        for (var commit : gitLog) {
            /*Cherche dans result si "commit.author" est déjà associé à un nb de commit:
            si c'est le cas renvoie le nb de commit
            sinon renvoie 0 */
            if(commit instanceof MergeCommit) {
                var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);

                /* met à jour le nb de commit avec put (remplace la valeur précédente associée à la clé)
                 * si la clé y est déjà  */
                result.commitsPerAuthor.put(commit.author, nb + 1);
            }
        }
        return result;
    }
}
