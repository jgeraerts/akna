package net.umask.akna.query;

import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.model.MaternalCase;
import static net.umask.akna.query.GetAllCasesQuery.maternalCaseDTOProjection;
import static net.umask.akna.query.GetAllCasesQuery.maternalCaseDTOTransformer;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Restrictions.idEq;

/**
 * User: Me
 * Date: 10-jan-2010
 * Time: 7:39:44
 */
public class GetCaseByIdQuery implements Query<MaternalCaseDTO> {
    private Long id;

    public GetCaseByIdQuery(Long id) {
        this.id = id;
    }

    @Override
    public MaternalCaseDTO execute(Session session) {
        return (MaternalCaseDTO) session.createCriteria(MaternalCase.class)
                .add(idEq(id))
                .setProjection(maternalCaseDTOProjection())
                .setResultTransformer(maternalCaseDTOTransformer())
                .uniqueResult();


    }
}
