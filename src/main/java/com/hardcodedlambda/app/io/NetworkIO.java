package com.hardcodedlambda.app.io;

import java.io.IOException;

public interface NetworkIO {

    String readLine() throws IOException;
    void writeLine(String line);
}
