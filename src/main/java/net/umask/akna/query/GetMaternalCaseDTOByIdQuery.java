package net.umask.akna.query;

import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.model.MaternalCase;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.idEq;
import static org.hibernate.transform.Transformers.aliasToBean;

/**
 * User: Me
 * Date: 7-jan-2010
 * Time: 20:52:20
 */
public class GetMaternalCaseDTOByIdQuery implements Query<MaternalCaseDTO> {
    private Long id;

    public GetMaternalCaseDTOByIdQuery(Long id) {

        this.id = id;
    }

    @Override
    public MaternalCaseDTO execute(Session session) {
        return (MaternalCaseDTO) session.createCriteria(MaternalCase.class)
                .add(idEq(id))
                .setProjection(
                        projectionList()
                                .add(property("id"), "id")
                                .add(property("body"), "body")
                                .add(property("title"), "title"))
                .setResultTransformer(aliasToBean(MaternalCaseDTO.class))
                .uniqueResult();
    }
}
