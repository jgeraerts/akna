package net.umask.akna.web.pages;

import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.query.GetCaseByIdQuery;
import net.umask.akna.web.MaternalWicketApplication;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 23-nov-2009
 * Time: 20:15:49
 */
public class MaternalCaseLoadableDetachableModel extends LoadableDetachableModel<MaternalCaseDTO> {
    private Long id;

    public MaternalCaseLoadableDetachableModel(MaternalCaseDTO maternalCase) {
        super(maternalCase);
        this.id = maternalCase.getId();
    }

    public MaternalCaseLoadableDetachableModel() {

        this(new MaternalCaseDTO());
    }

    public MaternalCaseLoadableDetachableModel(Long id) {
        this.id=id;
    }

    @Override
    protected MaternalCaseDTO load() {
        if (id == null) {
            return new MaternalCaseDTO();
        }
        return MaternalWicketApplication.get().getQueryService().executeQuery(new GetCaseByIdQuery(id));

    }
}
