package lnmiit.college.counsellingapp.mn.crawler.rview;

public class AnswerModel {
    private String faculty_name;
    private String answer_text;
    private String faculty_image_url;

    public AnswerModel(String faculty_name, String answer_text, String faculty_image_url) {
        this.faculty_name = faculty_name;
        this.answer_text = answer_text;
        this.faculty_image_url = faculty_image_url;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public String getFaculty_image_url() {
        return faculty_image_url;
    }
}
