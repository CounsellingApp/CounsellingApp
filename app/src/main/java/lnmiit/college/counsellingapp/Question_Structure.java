package lnmiit.college.counsellingapp;

import java.util.ArrayList;
import java.util.Date;

public class Question_Structure {
    private String question_id;
    private String question_text;
    private String asked_by = "Anonymous";
    private ArrayList<String> question_answers;
    private String tag;
    private Date date;
    private boolean answered;

}
