package com.example.itlearning;

public class Question {
    private String question, oA, oB, oC, oD, answer;

    public Question() {
    }

    public Question(String question, String oA, String oB, String oC, String oD, String answer) {
        this.question = question;
        this.oA = oA;
        this.oB = oB;
        this.oC = oC;
        this.oD = oD;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getoA() {
        return oA;
    }

    public String getoB() {
        return oB;
    }

    public String getoC() {
        return oC;
    }

    public String getoD() {
        return oD;
    }

    public String getAnswer() {
        return answer;
    }
}
