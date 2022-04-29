package com.yevgen.logcollection.service.impl;

import com.yevgen.logcollection.exception.FileNotFoundException;
import com.yevgen.logcollection.exception.FileReadException;
import com.yevgen.logcollection.filtering.FilterFactory;
import com.yevgen.logcollection.model.Log;
import com.yevgen.logcollection.model.request.FilterRequest;
import com.yevgen.logcollection.service.ILogCollectionService;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class LogCollectionService implements ILogCollectionService {

    private static Logger logger = LoggerFactory.getLogger(LogCollectionService.class);

    @Autowired
    private FilterFactory filterFactory;

    @Value("{log-collection.path}")
    private String path;

    @Value("{log-collection.file-reader.buffer-size:4096}")
    private Integer bufferSize;

    @Value("{log-collection.file-reader.encoding:4096}")
    private String encoding;

    @Value("{log-collection.file-reader.column-separator}")
    private String columnSeparator;

    @Override
    public List<Log> getLogs(String filename, Integer limit, FilterRequest filterRequest) {
        File file = getFile(filename);
        Predicate<Log> filter = filterRequest != null ? filterFactory.createFilter(filterRequest) : null;
        return readFromFile(file, limit, filter);
    }

    private File getFile(String filename) {
        File file = Path.of(path, filename).toFile();
        if (!file.exists()) {
            throw new FileNotFoundException(filename);
        }

        return file;
    }

    private List<Log> readFromFile(File file, Integer limit, Predicate<Log> filter) {
        try {
            ReversedLinesFileReader fileReader =
                    new ReversedLinesFileReader(file, bufferSize, Charsets.toCharset(encoding));

            List<Log> logs = new ArrayList<>();

            int i = 0;
            while (limit == null || i < limit) {
                String line = fileReader.readLine();
                if (line == null) {  // means we reached beginning of the file
                    break;
                }

                Log log = parseLine(line);
                if (filter.test(log)) {
                    logs.add(log);
                    i++;
                }
            }

            // sort by timestamp by desc
            logs.sort((l1, l2) -> l2.getDateTime().compareTo(l1.getDateTime()));

            return logs;
        } catch (Exception e) {
            throw new FileReadException(file.getName(), e);
        }
    }

    private Log parseLine(String line) {
        Log log = null;
        try {
            String[] columns = line.split(columnSeparator);
            if (columns.length < Log.LOG_COLUMNS_COUNT) {
                logger.warn("Skip line due to column count: " + line);
            }
            else {
                log = new Log(
                        LocalDateTime.parse(columns[0]),
                        columns[1],
                        columns[2]);
            }
        }
        catch (Exception e) {
            logger.warn("Unable to parse log: " + line);
        }
        return log;
    }

}
