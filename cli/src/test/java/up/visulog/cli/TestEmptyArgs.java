package up.visulog.cli;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de Test verifiant si makeConfigFromCommandLine retourne bien un Optional.empty()
 * lorsque son argument est vide
 *
 * @see CLILauncher
 */
public class TestEmptyArgs {

    /**
     * Fonction de test satisfaisant le contrat de la classe
     */
    @Test
    public void emptyArgs() {
        var config1 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{""});
        Assert.assertFalse(config1.isPresent());
        var config2 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{});
        Assert.assertFalse(config2.isPresent());
    }
}
