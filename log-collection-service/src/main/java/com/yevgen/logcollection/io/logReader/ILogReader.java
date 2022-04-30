package com.yevgen.logcollection.io.logReader;

import com.yevgen.logcollection.model.Log;

import java.io.IOException;

public interface ILogReader {

    // The implementation has to return null in case if no(more) logs to read
    Log readLog() throws IOException;

}
