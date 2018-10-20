package com.hardcodedlambda.app;

import java.io.IOException;

public interface IO {

    String readLine() throws IOException;
    void writeLine(String line);
}
