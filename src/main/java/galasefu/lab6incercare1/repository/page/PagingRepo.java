package galasefu.lab6incercare1.repository.page;



import galasefu.lab6incercare1.domain.Entity;
import galasefu.lab6incercare1.repository.Repository;
import galasefu.lab6incercare1.utils.paging.Page;
import galasefu.lab6incercare1.utils.paging.Pageable;

import java.sql.SQLException;

public interface PagingRepo<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAll(Pageable pageable) throws SQLException;
}