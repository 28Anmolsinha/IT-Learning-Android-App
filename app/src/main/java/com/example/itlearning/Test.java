package com.example.itlearning;

public class Test {
    private String testId, testTitle;

    public Test(){

    }
    public Test(String testId, String testTitle) {
        this.testId = testId;
        this.testTitle = testTitle;
    }

    public String getTestId() {
        return testId;
    }

    public String getTestTitle() {
        return testTitle;
    }
}
