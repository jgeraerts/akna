package net.umask.akna.query;

import java.util.List;

/**
 * User: Me
 * Date: 12-feb-2010
 * Time: 21:28:17
 */
public class FeedbackQueryResult {
    private List<String> queryFeedback;
    private List<String> answerFeedback;

    public FeedbackQueryResult(List<String> queryFeedback, List<String> answerFeedback) {

        this.queryFeedback = queryFeedback;
        this.answerFeedback = answerFeedback;
    }

    public List<String> getQuestionFeedback() {
        return queryFeedback;
    }

    public List<String> getAnswerFeedback() {
        return answerFeedback;
    }
}
