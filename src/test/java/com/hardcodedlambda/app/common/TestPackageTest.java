package com.hardcodedlambda.app.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestPackageTest {

    @Test
    public void toStringAndFromStringShouldReturnTheSameValue() {
        String nowString = "0000000001_2018-10-21T16:51:21.223_aaa";

        String result = TestPackage.fromString(nowString).toString();

        assertEquals(nowString, result);
    }

}