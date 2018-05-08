package com.example.krokken.quiz;

import android.util.SparseBooleanArray;

public class Question {

    private String mQuestions;

    private String mAnswer1, mAnswer2, mAnswer3, mAnswer4, mAnswer5, mAnswer6, mAnswer7, mAnswer8, mAnswer9, mAnswer10,
            mCorrect1, mCorrect2, mCorrect3, mCorrect4, mCorrect5, mTheTextAnswer, mPlayerName;

    private String[] mCorrectAnswerArray;
    private String[] mPossibleAnswersArray;
    public SparseBooleanArray mCheckBoxChecked;
    private static final int NO_ANSWER_PROVIDED = -1;
    private int mQuestionImage = NO_ANSWER_PROVIDED;
    private int mQuestionType;
    private int mQuestionNumber;

    private boolean mIsItChecked;
    private int mListViewPosition;
    private int mChildPosition;

    //Constructor for 10 possible answers, an image, a question, and 5 correct answers
    public Question(int questionType, int questionNumber, int questionImage, String question, String answer1, String answer2, String answer3, String answer4,
                    String answer5, String answer6, String answer7, String answer8, String answer9, String answer10, String correct1, String correct2, String correct3, String correct4, String correct5) {
        mQuestionType = questionType;
        mQuestionNumber = questionNumber;
        mQuestions = question;
        mAnswer1 = answer1;
        mAnswer2 = answer2;
        mAnswer3 = answer3;
        mAnswer4 = answer4;
        mAnswer5 = answer5;
        mAnswer6 = answer6;
        mAnswer7 = answer7;
        mAnswer8 = answer8;
        mAnswer9 = answer9;
        mAnswer10 = answer10;
        mPossibleAnswersArray = new String[]{mAnswer1, mAnswer2, mAnswer3, mAnswer4, mAnswer5, mAnswer6, mAnswer7, mAnswer8, mAnswer9, mAnswer10};
        mQuestionImage = questionImage;
        mCorrect1 = correct1;
        mCorrect2 = correct2;
        mCorrect3 = correct3;
        mCorrect4 = correct4;
        mCorrect5 = correct5;
        mCorrectAnswerArray = new String[]{mCorrect1, mCorrect2, mCorrect3, mCorrect4, mCorrect5};
        mCheckBoxChecked = new SparseBooleanArray(mPossibleAnswersArray.length);
    }

    //Constructor for 10 possible answers, no image, and 5 correct answers
    public Question(int questionType, int questionNumber, String question, String answer1, String answer2, String answer3, String answer4,
                    String answer5, String answer6, String answer7, String answer8, String answer9, String answer10, String correct1, String correct2, String correct3, String correct4, String correct5) {
        mQuestionType = questionType;
        mQuestionNumber = questionNumber;
        mQuestions = question;
        mAnswer1 = answer1;
        mAnswer2 = answer2;
        mAnswer3 = answer3;
        mAnswer4 = answer4;
        mAnswer5 = answer5;
        mAnswer6 = answer6;
        mAnswer7 = answer7;
        mAnswer8 = answer8;
        mAnswer9 = answer9;
        mAnswer10 = answer10;
        mPossibleAnswersArray = new String[]{mAnswer1, mAnswer2, mAnswer3, mAnswer4, mAnswer5, mAnswer6, mAnswer7, mAnswer8, mAnswer9, mAnswer10};
        mCorrect1 = correct1;
        mCorrect2 = correct2;
        mCorrect3 = correct3;
        mCorrect4 = correct4;
        mCorrect5 = correct5;
        mCorrectAnswerArray = new String[]{mCorrect1, mCorrect2, mCorrect3, mCorrect4, mCorrect5};
        mCheckBoxChecked = new SparseBooleanArray(mPossibleAnswersArray.length);
    }

    //Constructor for 10 possible answers, no image, and 2 correct answers
    public Question(int questionType, int questionNumber, String question, String answer1, String answer2, String answer3, String answer4, String answer5,
                    String answer6, String answer7, String answer8, String answer9, String answer10, String correct1, String correct2) {
        mQuestionType = questionType;
        mQuestionNumber = questionNumber;
        mQuestions = question;
        mAnswer1 = answer1;
        mAnswer2 = answer2;
        mAnswer3 = answer3;
        mAnswer4 = answer4;
        mAnswer5 = answer5;
        mAnswer6 = answer6;
        mAnswer7 = answer7;
        mAnswer8 = answer8;
        mAnswer9 = answer9;
        mAnswer10 = answer10;
        mPossibleAnswersArray = new String[]{mAnswer1, mAnswer2, mAnswer3, mAnswer4, mAnswer5, mAnswer6, mAnswer7, mAnswer8, mAnswer9, mAnswer10};
        mCorrect1 = correct1;
        mCorrect2 = correct2;
        mCorrectAnswerArray = new String[]{mCorrect1, mCorrect2};
        mCheckBoxChecked = new SparseBooleanArray(mPossibleAnswersArray.length);
    }

    //Constructor for 5 possible answers, an image, and a single correct mIsItAnswered
    public Question(int questionType, int questionNumber, int questionImage, String question, String answer1, String answer2, String answer3, String answer4,
                    String answer5, String correct1) {
        mQuestionType = questionType;
        mQuestionNumber = questionNumber;
        mQuestions = question;
        mAnswer1 = answer1;
        mAnswer2 = answer2;
        mAnswer3 = answer3;
        mAnswer4 = answer4;
        mAnswer5 = answer5;
        mPossibleAnswersArray = new String[]{mAnswer1, mAnswer2, mAnswer3, mAnswer4, mAnswer5};
        mQuestionImage = questionImage;
        mCorrect1 = correct1;
        mCorrectAnswerArray = new String[]{mCorrect1};
        mCheckBoxChecked = new SparseBooleanArray(mPossibleAnswersArray.length);
    }

    //Constructor for 5 possible answers, no image, and a single correct mIsItAnswered
    public Question(int questionType, int questionNumber, String question, String answer1, String answer2, String answer3, String answer4, String answer5,
                    String correct1) {
        mQuestionType = questionType;
        mQuestionNumber = questionNumber;
        mQuestions = question;
        mAnswer1 = answer1;
        mAnswer2 = answer2;
        mAnswer3 = answer3;
        mAnswer4 = answer4;
        mAnswer5 = answer5;
        mPossibleAnswersArray = new String[]{mAnswer1, mAnswer2, mAnswer3, mAnswer4, mAnswer5};
        mCorrect1 = correct1;
        mCorrectAnswerArray = new String[]{mCorrect1};
        mCheckBoxChecked = new SparseBooleanArray(mPossibleAnswersArray.length);
    }

    //EditText constructor
    public Question(int questionType, int questionNumber, int questionImage, String question, String theTextAnswer) {
        mQuestionType = questionType;
        mQuestionNumber = questionNumber;
        mQuestionImage = questionImage;
        mQuestions = question;
        mTheTextAnswer = theTextAnswer;
        mPossibleAnswersArray = new String[0];
        mCheckBoxChecked = new SparseBooleanArray(mPossibleAnswersArray.length);
    }

    //Test constructor
    public Question(int questionType, int questionNumber, String question, String answer1, String answer2, String answer3, String answer4, String answer5, int questionImage, String correct1) {
        mQuestionType = questionType;
        mQuestionNumber = questionNumber;
        mQuestionImage = questionImage;
        mQuestions = question;
        mAnswer1 = answer1;
        mAnswer2 = answer2;
        mAnswer3 = answer3;
        mAnswer4 = answer4;
        mAnswer5 = answer5;
        mPossibleAnswersArray = new String[]{mAnswer1, mAnswer2, mAnswer3, mAnswer4, mAnswer5};
        mCorrect1 = correct1;
        mCorrectAnswerArray = new String[]{mCorrect1};
        mCheckBoxChecked = new SparseBooleanArray(mPossibleAnswersArray.length);
    }

    public Question(int questionType, int questionNumber, String question, String answer1, String answer2, String answer3, String answer4, int questionImage, String correct1) {
        mQuestionType = questionType;
        mQuestionNumber = questionNumber;
        mQuestionImage = questionImage;
        mQuestions = question;
        mAnswer1 = answer1;
        mAnswer2 = answer2;
        mAnswer3 = answer3;
        mAnswer4 = answer4;
        mPossibleAnswersArray = new String[]{mAnswer1, mAnswer2, mAnswer3, mAnswer4, mAnswer5};
        mCorrect1 = correct1;
        mCorrectAnswerArray = new String[]{mCorrect1};
        mCheckBoxChecked = new SparseBooleanArray(mPossibleAnswersArray.length);
    }

    public int getQuestionType() {
        return mQuestionType;
    }

    public int getQuestionNumber() {
        return mQuestionNumber;
    }

    public String getQuestions() {
        return mQuestions;
    }

    public String[] getAnswersArray() {
        return mPossibleAnswersArray;
    }

    public String getTheTextAnswer() {
        return mTheTextAnswer;
    }

    public String[] getCorrectArray() {
        return mCorrectAnswerArray;
    }

    public String getCorrect1() {
        return mCorrect1;
    }

    public String getCorrect2() {
        return mCorrect2;
    }

    public int getImageResourceId() {
        return mQuestionImage;
    }

    public boolean hasImage() {
        return mQuestionImage != NO_ANSWER_PROVIDED;
    }

    public void setName(String name) {
        mPlayerName = name;
    }

    public String getName() {
        return mPlayerName;
    }

    public boolean getIsItChecked() {
        return mIsItChecked;
    }

    public int getListViewPosition() {
        return mListViewPosition;
    }

    public int getChildPosition() {
        return mChildPosition;
    }

    public void setIsItChecked(boolean checked) {
        mIsItChecked = checked;
    }

    public void setListViewPosition(int listPosition) {
        mListViewPosition = listPosition;
    }

    public void setChildPosition(int childPosition) {
        mChildPosition = childPosition;
    }

    public void putsCheckBoxBoolean(int i, boolean checked) {
        this.mCheckBoxChecked.put(i, checked);
    }

    public boolean getCheckBoxBoolean(int i) {
        return this.mCheckBoxChecked.get(i);
    }
}

