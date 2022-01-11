package up.visulog.gitrawdata;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCommit {
    public static void main(String[] args) {

        BufferedReader mergeCommit = ChangesDescription.processCommand(new String[]{"git", "log"}, Paths.get("."));
        List<Commit> commitList = Commit.parseLog(mergeCommit);
        for (Commit c : commitList) {
            if (c instanceof MergeCommit) System.out.println(c);
        }
    }

    @Test
    public void testParseCommit() throws IOException, URISyntaxException {
        var expected = "Commit{id='6304c1acdc1cbdeb8315528781896abc72a021b8', date='" + new GregorianCalendar(2020, Calendar.SEPTEMBER, 1, 12, 30, 53).getTime() + "', author='Aldric Degorre <adegorre@irif.fr>', description='More gradle configuration (with subprojects)'}";
        var uri = Objects.requireNonNull(getClass().getClassLoader().getResource("git.log")).toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var commit = Commit.parseCommit(reader);
            assertTrue(commit.isPresent());
            assertEquals(expected, commit.get().toString());
        }
    }

    @Test
    public void testParseLog() throws IOException, URISyntaxException {
        var expected = "[Commit{id='6304c1acdc1cbdeb8315528781896abc72a021b8', " + "date='" + new GregorianCalendar(2020, Calendar.SEPTEMBER, 1, 12, 30, 53).getTime() + "', author='Aldric Degorre <adegorre@irif.fr>', description='More gradle configuration (with subprojects)'}, " + "Commit{id='c0cf37d6b32897677e4b8f04be012e5379a7ab80', date='" + new GregorianCalendar(2020, Calendar.AUGUST, 27, 23, 49, 3).getTime() + "', author='Aldric Degorre <adegorre@irif.fr>', description='first setup of project modules and gradle configuration'}, " + "Commit{id='9e74f1581f23aaad21e2b936091d3ce371336e22', date='" + new GregorianCalendar(2020, Calendar.AUGUST, 31, 11, 28, 28).getTime() + "', author='Aldric Degorre <adegorre@irif.fr>', description='Update README.md - more modules'}, " + "Commit{id='7484b0cb7b4e69e09c82ed38549750fa2a77f50c', date='" + new GregorianCalendar(2020, Calendar.AUGUST, 27, 0, 35, 19).getTime() + "', author='Aldric Degorre <adegorre@irif.fr>', description='Update README.md - translation...'}, " + "Commit{id='9aaf6e09cc30909b32c68b4d5bf4ac50f95ecb93', date='" + new GregorianCalendar(2020, Calendar.AUGUST, 27, 0, 33, 46).getTime() + "', author='Aldric Degorre <adegorre@irif.fr>', description='Update README.md - some title left untranslated'}, " + "Commit{id='969e2247156f27f27fec57b13faf6097bf4e2757', date='" + new GregorianCalendar(2020, Calendar.AUGUST, 27, 0, 32, 47).getTime() + "', author='Aldric Degorre <adegorre@irif.fr>', description='Update README.md -> in English, with some more details'}, " + "Commit{id='486d76dbfd24ac65eeeeb16e57ae4fd68c8ecb1c', date='" + new GregorianCalendar(2020, Calendar.AUGUST, 27, 0, 2, 55).getTime() + "', author='Aldric Degorre <adegorre@irif.fr>', description='Ajout de README.md avec définition des grandes lignes du sujet.'}]";
        var uri = Objects.requireNonNull(getClass().getClassLoader().getResource("git.log")).toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var log = Commit.parseLog(reader);
            System.out.println(log);
            assertEquals(expected, log.toString());
        }
    }

}
