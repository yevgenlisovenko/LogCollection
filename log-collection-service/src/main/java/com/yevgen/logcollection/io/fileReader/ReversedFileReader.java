package com.yevgen.logcollection.io.fileReader;

import com.yevgen.logcollection.exception.FileReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReversedFileReader implements IFileReader {

    private static Logger logger = LoggerFactory.getLogger(ReversedFileReader.class);

    private final int bufferSize;
    private RandomAccessFile randomAccessFile;
    private long currPos;

    public ReversedFileReader(File file, int bufferSize) {
        try {
            this.randomAccessFile = new RandomAccessFile(file, "r");
            currPos = randomAccessFile.length();
        } catch (IOException e) {
            throw new FileReadException(file.getAbsolutePath(), e);
        }
        this.bufferSize = bufferSize;
    }

    // I didn't want to read from the end byte by byte. I think it will be faster to read chunks of bytes from file (from the end of file).
    // So, the method reads from the end of file to buffer in a loop and checks in each iteration if buffer has EOL.
    // If buffer has EOL or beginning of the file reached then we have a line.
    @Override
    public String readLine() throws IOException {

        List<byte[]> byteChunks = new ArrayList<>();
        int lineSize = 0;
        boolean beginningOfLineReached = false;

        while (currPos > 0 && !beginningOfLineReached) {

            // Position the cursor and determine bytesToRead
            int bytesToRead = bufferSize;
            if (currPos - bufferSize < 0) {
                // means we reached beginning of the file so buffer will be less
                bytesToRead = (int)currPos;
                currPos = 0;
            }
            else {
                currPos -= bufferSize;
            }
            randomAccessFile.seek(currPos);

            // Read to buffer
            byte[] buffer = new byte[bytesToRead];
            int readBytes = randomAccessFile.read(buffer);
            logger.debug("Buffer: {}", new String(buffer));

            // Scan the bytes for EOL (\r\n or \n), where \n - 0xA, \r - 0xD.
            // Also reposition cursor to the end of next line
            int startOfLine = -1;
            for (int i = readBytes - 1; i >=0; i--) {
                if (buffer[i] == 0xA) {
                    beginningOfLineReached = true;
                    startOfLine = i + 1;
                    currPos += i;
                    if (i > 0 && buffer[i - 1] == 0xD) {
                        currPos--;
                    }
                    break;
                }
            }

            // Add bytes to chunks
            if (startOfLine != -1) {
                byteChunks.add(Arrays.copyOfRange(buffer, startOfLine, buffer.length));
                lineSize += (buffer.length - startOfLine);
            }
            else {
                byteChunks.add(buffer);
                lineSize += buffer.length;
            }
        }

        return lineSize == 0 && currPos == 0 ? null : byteChunksToString(byteChunks, lineSize);
    }

    private String byteChunksToString(List<byte[]> byteChunks, int lineSize) {
        byte[] line = new byte[lineSize];

        int i = 0;
        for (int j = byteChunks.size() - 1; j >= 0; j--) {
            for (int k = 0; k < byteChunks.get(j).length; k++) {
                line[i++] = byteChunks.get(j)[k];
            }
        }

        return new String(line);
    }

}
