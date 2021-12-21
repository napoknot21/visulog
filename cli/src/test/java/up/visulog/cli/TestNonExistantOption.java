package up.visulog.cli;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Classe de test verifiant le bon comportement de makeConfigFromCommandLineArgs en cas d'option inexistante
 *
 * @see CLILauncher
 */

public class TestNonExistantOption {

    /**
     * Fonction de test
     */
    @Test
    public void nonExistOpt() {
        var NonExistOpt = CLILauncher.makeConfigFromCommandLineArgs(new String[]{"--nonExistingOption"});
        assertFalse(NonExistOpt.isPresent());
    }
}
