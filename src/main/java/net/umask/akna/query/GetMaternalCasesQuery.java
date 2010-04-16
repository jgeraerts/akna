package net.umask.akna.query;

import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.model.MaternalCase;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.transform.Transformers.aliasToBean;

import java.util.List;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 21:36:49
 */
public class GetMaternalCasesQuery<T> implements Query<List<MaternalCaseDTO>> {
    @SuppressWarnings({"unchecked"})
    @Override
    public List<MaternalCaseDTO> execute(Session session) {
        return session.createCriteria(MaternalCase.class)
                .setProjection(projectionList()
                        .add(property("id"), "id")
                        .add(property("title"), "title"))
                .setResultTransformer(aliasToBean(MaternalCaseDTO.class))
                .list();

    }
}
