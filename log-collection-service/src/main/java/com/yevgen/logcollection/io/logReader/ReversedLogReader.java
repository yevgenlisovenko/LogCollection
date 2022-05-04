package com.yevgen.logcollection.io.logReader;

import com.yevgen.logcollection.exception.FileReadException;
import com.yevgen.logcollection.io.fileReader.IFileReader;
import com.yevgen.logcollection.io.fileReader.ReversedFileReader;
import com.yevgen.logcollection.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This implementation of the ILogReader reads logs from the end of file.
// The reader also uses internal buffer to read from the log file.
public class ReversedLogReader implements ILogReader {

    private static Logger logger = LoggerFactory.getLogger(ReversedLogReader.class);

    private IFileReader fileReader;
    private String columnSeparator;
    private DateTimeFormatter dateTimeFormatter;

    public ReversedLogReader(File file, int bufferSize, String columnSeparator, String dateFormat) {
        this.columnSeparator = columnSeparator;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            fileReader = new ReversedFileReader(file, bufferSize);
        } catch (Exception e) {
            throw new FileReadException(file.getName(), e);
        }
    }

    @Override
    public Log readLog() throws IOException {
        Log log = null;
        boolean parsedSuccessfully = true;
        do {
            String line = fileReader.readLine();
            if (line == null) {
                break;
            }
            log = parseLine(line);
            parsedSuccessfully = log != null;
        }
        while (!parsedSuccessfully);  // to skip lines which were not parsed successfully
        return log;
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
                        LocalDateTime.parse(columns[0], dateTimeFormatter),
                        columns[1],
                        columns[2]);
            }
        }
        catch (Exception e) {
            logger.warn(String.format("Unable to parse log: %s, error: %s", line, e.getMessage()));
        }
        return log;
    }
}
