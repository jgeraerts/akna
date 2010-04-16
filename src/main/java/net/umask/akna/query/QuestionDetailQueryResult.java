package net.umask.akna.query;

import net.umask.akna.dto.AnswerDTO;
import net.umask.akna.dto.QuestionDTO;

import java.io.Serializable;
import java.util.List;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 18:56:45
 */
public class QuestionDetailQueryResult implements Serializable {
    private List<AnswerDTO> answers;
    private QuestionDTO question;

    public QuestionDetailQueryResult(QuestionDTO dto, List<AnswerDTO> answers) {
        this.question = dto;
        this.answers = answers;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public QuestionDTO getQuestion() {
        return question;
    }
}
