package com.yevgen.logcollection.service;

import com.yevgen.logcollection.exception.FileNotFoundException;
import com.yevgen.logcollection.model.Log;
import com.yevgen.logcollection.model.request.FilterRequest;
import com.yevgen.logcollection.model.request.FilterType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class LogCollectionServiceTest {

    @Autowired
    private ILogCollectionService logCollectionService;

    @Test(expected = FileNotFoundException.class)
    public void testLogCollectionService_FileDoesNotExists() {
        logCollectionService.getLogs("unexisting.log", 100, null);
    }

    @Test
    public void testLogCollectionService_EmptyFile() {
        List<Log> logs = logCollectionService.getLogs("empty.log", null, null);
        Assert.assertEquals(0, logs.size());
    }

    @Test
    public void testLogCollectionService_WithLimit() {
        List<Log> logs = logCollectionService.getLogs("sample.log", 50, null);
        Assert.assertEquals(50, logs.size());
        // test that the latest log is first
        Assert.assertEquals("Apple80211Set:10324 CFType is CFData", logs.get(0).getMessage());
    }

    @Test
    public void testLogCollectionService_NoLimit() {
        List<Log> logs = logCollectionService.getLogs("sample.log", null, null);
        Assert.assertEquals(53, logs.size());
    }

    @Test
    public void testLogCollectionService_WithLimitGreaterThanLogsCount() {
        List<Log> logs = logCollectionService.getLogs("sample.log", 100, null);
        Assert.assertEquals(53, logs.size());
    }

    @Test
    public void testLogCollectionService_HandleInvalidLogFormat() {
        List<Log> logs = logCollectionService.getLogs("invalid-format.log", null, null);
        Assert.assertEquals(3, logs.size());
        Assert.assertEquals("Message4", logs.get(0).getMessage());
        Assert.assertEquals("Message3", logs.get(1).getMessage());
        Assert.assertEquals("Message1", logs.get(2).getMessage());
    }

    @Test
    public void testLogCollectionService_FilterContains() {
        String filterString = "APPLE";
        List<Log> logs = logCollectionService.getLogs("sample.log", null,
                new FilterRequest(FilterType.Contains, filterString));
        Assert.assertEquals(10, logs.size());
        logs.forEach(l -> Assert.assertTrue(l.getMessage().contains(filterString)));
    }

    @Test
    public void testLogCollectionService_FilterStartsWith() {
        String filterString = "<airport";
        List<Log> logs = logCollectionService.getLogs("sample.log", null,
                new FilterRequest(FilterType.StartsWith, filterString));
        Assert.assertEquals(33, logs.size());
        logs.forEach(l -> Assert.assertTrue(l.getMessage().startsWith(filterString)));
    }

    @Test
    public void testLogCollectionService_FilterEndsWith() {
        String filterString = "request";
        List<Log> logs = logCollectionService.getLogs("sample.log", null,
                new FilterRequest(FilterType.EndsWith, filterString));
        Assert.assertEquals(14, logs.size());
        logs.forEach(l -> Assert.assertTrue(l.getMessage().endsWith(filterString)));
    }

    @Test
    public void testLogCollectionService_FilterRegex() {
        String filterString = "\\s+\\d{3}\\s+";
        List<Log> logs = logCollectionService.getLogs("sample.log", null,
                new FilterRequest(FilterType.Regex, filterString));
        Assert.assertEquals(5, logs.size());
        Pattern pattern = Pattern.compile(filterString);
        logs.forEach(l -> Assert.assertTrue(pattern.matcher(l.getMessage()).find()));
    }

    @Test
    public void testLogCollectionService_FilterLevel() {
        String filterString = "ERROR";
        List<Log> logs = logCollectionService.getLogs("sample.log", null,
                new FilterRequest(FilterType.Level, filterString));
        Assert.assertEquals(11, logs.size());
        logs.forEach(l -> Assert.assertEquals(LogLevel.ERROR, l.getLevel()));
    }

}
