package com.yevgen.logcollection.service.impl;

import com.yevgen.logcollection.exception.FileNotFoundException;
import com.yevgen.logcollection.exception.FileReadException;
import com.yevgen.logcollection.filtering.FilterFactory;
import com.yevgen.logcollection.io.LogReaderFactory;
import com.yevgen.logcollection.io.logReader.ILogReader;
import com.yevgen.logcollection.model.Log;
import com.yevgen.logcollection.model.request.FilterRequest;
import com.yevgen.logcollection.service.ILogCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class LogCollectionService implements ILogCollectionService {

    private static Logger logger = LoggerFactory.getLogger(LogCollectionService.class);

    @Autowired
    private FilterFactory filterFactory;

    @Autowired
    private LogReaderFactory logReaderFactory;

    @Value("${log-collection.path:/var/log}")
    private String path;

    @Override
    public List<Log> getLogs(String filename, Integer limit, FilterRequest filterRequest) {
        File file = getFile(filename);
        Predicate<Log> filter = filterRequest != null ? filterFactory.createFilter(filterRequest) : null;

        logger.info("Reading logs from {}", file.getAbsolutePath());

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
            ILogReader fileReader = logReaderFactory.getReader(file);

            List<Log> logs = new ArrayList<>();

            int i = 0;
            while (limit == null || i < limit) {
                Log log = fileReader.readLog();
                if (log == null) {  // means we reached beginning of the file
                    break;
                }

                if (filter == null || filter.test(log)) {
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

}
