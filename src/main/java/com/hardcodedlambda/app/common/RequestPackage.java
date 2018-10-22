package com.hardcodedlambda.app.common;

import java.time.LocalDateTime;
import java.util.Arrays;

public class RequestPackage {

    private static final String ID_FORMAT = "%010d";
    private static final String DELIMITER = "_";
    private static final char FILLER = 'a';

    private int id;
    private LocalDateTime time;
    private int size;


    public RequestPackage(int id, LocalDateTime time, int size) {
        this.id = id;
        this.time = time;
        this.size = size;
    }

    public static RequestPackage fromString(String packageText) {

        String[] packageComponent = packageText.split(DELIMITER);

        int id = Integer.valueOf(packageComponent[0]);
        LocalDateTime time = LocalDateTime.parse(packageComponent[1]);

        return new RequestPackage(id, time, packageText.length());
    }

    public RequestPackage copyWithUpdatedTime(LocalDateTime time) {
        return new RequestPackage(id, time, size);
    }

    @Override
    public String toString() {

        String dateString = time.toString();
        String id =  String.format(ID_FORMAT, this.id);

        String randomText =
                randomStringOfLength(size - id.length() - dateString.length() - (2 * DELIMITER.length()));

        return id + DELIMITER + dateString + DELIMITER + randomText;
    }

    private String randomStringOfLength(int length) {

        char[] charArray = new char[length];
        Arrays.fill(charArray, FILLER);
        return String.valueOf(charArray);
    }
}
