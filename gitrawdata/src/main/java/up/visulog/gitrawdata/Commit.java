package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Commit extends ChangesDescription{
    public List<String> files = new ArrayList<>();

    public Commit(BigInteger id, String author, Date date, String description) { // simplement le constructeur
        super(id, author, date, description);
    }

    public static List<Commit> parseLogFromCommand (Path gitPath) {  //renvoie la liste des commits selon le chemin git saisi en argument
        String[] args = {"git","log"};
        return parseLog(ChangesDescription.processCommand (args ,gitPath));
    }

    public static List<Commit> parseLog(BufferedReader reader) { //analyse le log de git et renvoie la liste des commits contenus
        var result = new ArrayList<Commit>();
        Optional<Commit> commit = parseCommit(reader);
        while (commit.isPresent()) {
            result.add(commit.get());
            commit = parseCommit(reader);
        }
        return result;
    }

    /**
     * Parses a log item and outputs a commit object. Exceptions will be thrown in case the input does not have the proper format.
     * Returns an empty optional if there is nothing to parse anymore.
     */
    public static Optional<Commit> parseCommit(BufferedReader input) { //analyse une partie du log de git et crée le commit associé puis le renvoie
        try {

            var line = input.readLine(); //lis la premiere ligne du log pour verifier qu'on est bien face à un commit (ou MergeCommit)
            if (line == null) return Optional.empty(); // if no line can be read, we are done reading the buffer
            var idChunks = line.split(" ");
            if (!idChunks[0].equals("commit")) parseError();

            line = input.readLine(); //lis la seconde ligne du log pour voir son type (Commit ou MergeCommit)
            var builder = new CommitBuilder(idChunks[1]);
            if (line.substring(0, line.indexOf(":")).equals("Merge")){
                builder = new MergeCommitBuilder(idChunks[1]);
            }/*Lis les valeurs du log et  determine si l'on va construire un CommitBuilder ou un MergeCommitBuilder */
            while (!line.isEmpty()) {
                var colonPos = line.indexOf(":");
                var fieldName = line.substring(0, colonPos);
                var fieldContent = line.substring(colonPos + 1).trim();
                builder.CommitConfig(fieldName, fieldContent); /*prepare le CommitBuilder (ou MergeCommitBuilder) en fonction
                des valeurs dans le log  */
                line = input.readLine(); //prepare next iteration
                if (line == null) parseError(); // end of stream is not supposed to happen now (commit data incomplete)
            }

            // now read the commit message per se
            var description = input
                    .lines() // get a stream of lines to work with
                    .takeWhile(currentLine -> !currentLine.isEmpty()) // take all lines until the first empty one (commits are separated by empty lines). Remark: commit messages are indented with spaces, so any blank line in the message contains at least a couple of spaces.
                    .map(String::trim) // remove indentation
                    .reduce("", (accumulator, currentLine) -> accumulator + currentLine); // concatenate everything
            builder.setDescription(description);
            return Optional.of(builder.createCommit());
        } catch (IOException e) {
            parseError();
        }
        return Optional.empty(); // this is supposed to be unreachable, as parseError should never return
    } // jusque là environ je n'y comprends RIEN

    // Helper function for generating parsing exceptions. This function *always* quits on an exception. It *never* returns.
    private static void parseError() { // Une fonction à utiliser quand on a une erreur
        throw new RuntimeException("Wrong commit format.");
    }

    @Override
    public String toString() { // la méthode pour afficher les caractéristiques d'un commit
        return "Commit{" +
                "id='" + id.toString(16) + '\'' +
                ", date='" + date.toString() + '\'' +
                ", author='" + author + '\'' +
                (description != null ? (", description='" + description + '\'') : "" )+ //the other optional field
                '}';
    }

}
