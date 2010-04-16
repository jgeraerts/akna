package net.umask.akna.web.pages;

import net.umask.akna.dto.AnswerDTO;
import net.umask.akna.query.GetAnswerDTOQueryById;
import net.umask.akna.web.MaternalWicketApplication;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * User: Me
 * Date: 29-dec-2009
 * Time: 17:29:31
 */
public class AnswerLoadableDetacheableModel extends LoadableDetachableModel<AnswerDTO> {
    private Long id;

    public AnswerLoadableDetacheableModel(long id) {
        this.id = id;
    }

    public AnswerLoadableDetacheableModel() {

    }

    @Override
    protected AnswerDTO load() {
        if (id == null) {
            return new AnswerDTO();
        }
        return MaternalWicketApplication.get().getQueryService().executeQuery(new GetAnswerDTOQueryById(id));
    }
}
