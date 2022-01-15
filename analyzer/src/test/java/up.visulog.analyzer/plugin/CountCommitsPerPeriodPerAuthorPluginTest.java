package up.visulog.analyzer.plugin;

import org.junit.Assert;
import org.junit.Test;
import up.visulog.gitrawdata.Commit;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class CountCommitsPerPeriodPerAuthorPluginTest {

    @Test
    public void processLog() {
        Random random = new Random();
        Calendar calendar = new GregorianCalendar();
        calendar.add(random.nextInt(Calendar.DST_OFFSET), -random.nextInt(100));
        var list = Commit.parseLogFromCommand(Path.of("."));
        Assert.assertFalse(list.isEmpty());
        var result = CountCommitsPerAuthorPerPeriod.processLog(list, calendar.getTime(), new Date());
        Assert.assertNotNull(result);
        Assert.assertFalse(result.getResultAsMap().isEmpty());
        Assert.assertFalse(result.getResultAsString().isBlank());
        Assert.assertFalse(result.getResultAsHtmlDiv().isBlank());
    }
}