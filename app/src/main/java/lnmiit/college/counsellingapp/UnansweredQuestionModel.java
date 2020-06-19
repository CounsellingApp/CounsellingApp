package lnmiit.college.counsellingapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class UnansweredQuestionModel {
    private String question_id;
    private String question;
    private String asked_by = "Anonymous";
    private Map<String,String> faculty_answers;
    private String tag;
    private Date date;
    private String userid;
    private boolean isanswered;

    public UnansweredQuestionModel(String question_id, String question, String asked_by, Map<String, String> faculty_answers, String tag, Date date, String userid, boolean answered) {
        this.question_id = question_id;
        this.question = question;
        this.asked_by = asked_by;
        this.faculty_answers = faculty_answers;
        this.tag = tag;
        this.date = date;
        this.userid = userid;
        this.isanswered = answered;
    }

    public UnansweredQuestionModel() {
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion_text(String question_text) {
        this.question = question_text;
    }

    public String getAsked_by() {
        return asked_by;
    }

    public void setAsked_by(String asked_by) {
        this.asked_by = asked_by;
    }

    public Map<String, String> getFaculty_answers() {
        return faculty_answers;
    }

    public void setFaculty_answers(Map<String, String> faculty_answers) {
        this.faculty_answers = faculty_answers;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isAnswered() {
        return isanswered;
    }

    public void setAnswered(boolean answered) {
        this.isanswered = answered;
    }



}
