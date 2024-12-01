package use_case.create_question;

/**
 * The input data for custom question creation.
 */
public class QuestionCreationInputData {

    private String questionText;
    private String answer;
    private String wrong1;
    private String wrong2;
    private String wrong3;

    public QuestionCreationInputData(String questionText, String answer, String wrong1, String wrong2, String wrong3) {
        this.questionText = questionText;
        this.answer = answer;
        this.wrong1 = wrong1;
        this.wrong2 = wrong2;
        this.wrong3 = wrong3;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public String getWrong1() {
        return wrong1;
    }

    public String getWrong2() {
        return wrong2;
    }

    public String getWrong3() {
        return wrong3;
    }
}
