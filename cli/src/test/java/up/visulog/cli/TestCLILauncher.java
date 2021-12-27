package up.visulog.cli;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import up.visulog.config.Configuration;

import java.io.File;

/**
 * Classe test de CLILauncher.
 *
 * @see CLILauncher
 */
public class TestCLILauncher {

    private static File dir;


    /**
     * Cree le repertoire de stockage de sauvegarde de commande, le supprime s'il existe déjà
     */
    @BeforeClass
    public static void setup() {
        dir = new File("Files");
        eraseFile(dir);
        dir.mkdir();
        Configuration.createModifFile("example", "--addPlugin=CountMergeCommitsPerAuthor\n" +
                "--allPlugin\n" +
                "Here are examples of possible syntax of the configuration file");
    }

    /**
     * Supprime le repertoire utilise pour stocker les sauvegardes de commandes
     */
    private static void eraseFile(File file) {
        if (!file.exists()) return;
        var listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            file.delete();
            return;
        }
        for (var f : listFiles) {
            eraseFile(f);
        }
        file.delete();
    }

    @AfterClass
    public static void clean() {
        eraseFile(dir);
    }

    /**
     * Teste si l'appel des differents plugins fonctionne bien.
     */
    @Test
    public void ValidPlugin() {
        var countCommit = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                ".", "--addPlugin=CountCommitsPerAuthor"
        });
        Assert.assertTrue(countCommit.isPresent());

        var countMCommit = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--addPlugin=CountMergeCommitsPerAuthor"
        });
        Assert.assertTrue(countMCommit.isPresent());
        var countLines = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--addPlugin=CountLinesPerAuthor"
        });
        Assert.assertTrue(countLines.isPresent());
        var allPlugin = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--allPlugin"
        });
        Assert.assertTrue(allPlugin.isPresent());
    }

    /**
     * teste si makeConfigFromCommandLine retourne bien un Optional.empty()
     * lorsque son argument est vide.
     */

    @Test
    public void emptyArgs() {
        var config1 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{""});
        Assert.assertFalse(config1.isPresent());
        var config2 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{});
        Assert.assertFalse(config2.isPresent());
    }

    /**
     * Verifie le bon comportement de makeConfigFromCommandLineArgs en cas d'option inexistante.
     */
    @Test
    public void nonExistOpt() {
        var NonExistOpt = CLILauncher.makeConfigFromCommandLineArgs(new String[]{"--nonExistingOption"});
        Assert.assertFalse(NonExistOpt.isPresent());
    }

    /**
     * Teste le bon comportement du main.
     */
    @Test
    public void main() {
        CLILauncher.main(new String[0]);
    }

    /**
     * Teste la bonne creation des fichiers de sauvegarde de commande.
     */
    @Test
    public void testLoadConfig() {
        CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--save=--allPlugin"
        });
        CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--save=CountCommitsPerAuthor"
        });
        var config = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--load=CountCommitsPerAuthor"
        });
        Assert.assertTrue(config.isPresent());

        var config2 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--load=example"
        });
        Assert.assertTrue(config2.isPresent());
    }

    /**
     * teste si la sauvegarde de fichier donne le bon return sur makeConfigFromCommand()
     */
    @Test
    public void testSave() {
        String command = "--allPlugin";
        var config = CLILauncher.makeConfigFromCommandLineArgs(new String[]{
                "--save=" + command
        });
        Assert.assertFalse(config.isPresent());
    }
}
