package up.visulog.cli;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCLILauncher {
    /*
    TODO: one can also add integration tests here:
    - run the whole program with some valid options and look whether the output has a valid format
    - run the whole program with bad command and see whether something that looks like help is printed
     */
    @Test
    public static void testArgumentParser() {
        var config1 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{".", "--addPlugin=countCommits"});
        assertTrue(config1.isPresent());
        var config2 = CLILauncher.makeConfigFromCommandLineArgs(new String[] {
            "--nonExistingOption"
        });
        assertFalse(config2.isPresent());
        var config3 = CLILauncher.makeConfigFromCommandLineArgs(new String[] {
                "--addPlugin=countMergeCommits"
        });
        assertTrue(config3.isPresent());
        if(config3.isPresent()) System.out.println("Oui"); else { System.out.println("Non");}
    }

    public static void main(String[] args) {
        //testArgumentParser();
        String [] ar = new String[]{"--addPlugin=afkjkf","--alkdlfj","encore dans le deuxième", "--plus dans le deuxieme"};
        ArrayList<String> aa = CLILauncher.inputFiltering(ar);
        for(String s : aa) System.out.println(s);

        String [] a = new String[0];
        ArrayList<String> aaa = CLILauncher.inputFiltering(a);
        for(String s : aaa) System.out.println(s);

    }
}
