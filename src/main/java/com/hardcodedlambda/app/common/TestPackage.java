package com.hardcodedlambda.app.common;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TestPackage {

    private static final String ID_FORMAT = "%010d";
    private static final String DELIMITER = "_";

    private Integer id;
    private LocalDateTime time;
    private int size;


    public TestPackage(int id, LocalDateTime time, int size) {
        this.id = id;
        this.time = time;
        this.size = size;
    }

    public static TestPackage fromString(String packageText) {

        String[] packageComponent = packageText.split("_");

        int id = Integer.valueOf(packageComponent[0]);
        LocalDateTime time = LocalDateTime.parse(packageComponent[1]);

        return new TestPackage(id, time, packageText.length());
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
        Arrays.fill(charArray, 'a');
        return String.valueOf(charArray);
    }
}
