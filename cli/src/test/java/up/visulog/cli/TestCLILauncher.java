package up.visulog.cli;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Classe Test verifiant si l'appel des differents plugins fonctionne bien
 *
 * @see CLILauncher
 */
public class TestCLILauncher {

    /**
     * Fonction de test
     */
    @Test
    public void testArgumentParser() {
        var countCommit = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                ".", "--addPlugin=CountCommitsPerAuthor"
        });
        assertTrue(countCommit.isPresent());

        var countMCommit = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--addPlugin=CountMergeCommitsPerAuthor"
        });
        assertTrue(countMCommit.isPresent());
        var countLines = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--addPlugin=CountLinesPerAuthor"
        });
        assertTrue(countLines.isPresent());
        var allPlugin = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--allPlugin"
        });
        assertTrue(allPlugin.isPresent());
    }

}
