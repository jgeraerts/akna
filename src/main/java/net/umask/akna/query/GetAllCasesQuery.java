package net.umask.akna.query;

import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.model.MaternalCase;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import org.hibernate.transform.ResultTransformer;
import static org.hibernate.transform.Transformers.aliasToBean;

import java.util.List;

/**
 * User: Me
 * Date: 10-jan-2010
 * Time: 7:31:26
 */
public class GetAllCasesQuery implements Query<List<MaternalCaseDTO>> {
    private int first;
    private int count;

    public GetAllCasesQuery(int first, int count) {
        this.first = first;
        this.count = count;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<MaternalCaseDTO> execute(Session session) {
        return session.createCriteria(MaternalCase.class)
                .setProjection(maternalCaseDTOProjection())
                .setResultTransformer(maternalCaseDTOTransformer())
                .setFirstResult(first)
                .setMaxResults(count)
                .list();
    }

    public static ResultTransformer maternalCaseDTOTransformer() {
        return aliasToBean(MaternalCaseDTO.class);
    }

    public static ProjectionList maternalCaseDTOProjection() {
        return projectionList()
                .add(property("id"), "id")
                .add(property("title"), "title")
                .add(property("body"), "body");
    }
}
