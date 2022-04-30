package com.yevgen.logcollection.io;

import com.yevgen.logcollection.io.logReader.ILogReader;
import com.yevgen.logcollection.io.logReader.ReversedLogReader;
import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LogReaderFactory {

    @Value("${log-collection.file-reader.buffer-size:4096}")
    private Integer bufferSize;

    @Value("${log-collection.file-reader.encoding:UTF-8}")
    private String encoding;

    @Value("${log-collection.file-reader.column-separator}")
    private String columnSeparator;

    @Value("${log-collection.file-reader.date-format}")
    private String dateFormat;

    public ILogReader getReader(File file) {
        return new ReversedLogReader(file, bufferSize, Charsets.toCharset(encoding), columnSeparator, dateFormat);
    }
}
