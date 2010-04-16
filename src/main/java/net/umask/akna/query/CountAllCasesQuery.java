package net.umask.akna.query;

import net.umask.akna.model.MaternalCase;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Projections.rowCount;

/**
 * User: Me
 * Date: 10-jan-2010
 * Time: 7:32:27
 */
public class CountAllCasesQuery implements Query<Integer> {
    @Override
    public Integer execute(Session session) {
        return ((Long) session.createCriteria(MaternalCase.class)
                .setProjection(rowCount())
                .uniqueResult()).intValue();
    }
}
