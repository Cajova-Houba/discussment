package org.danekja.discussment.core.accesscontrol.dao.jpa;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.dao.jpa.GenericDaoJPA;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Martin Bláha on 04.05.17.
 */

public class PermissionDaoJPA extends GenericDaoJPA<Long, Permission> implements PermissionDao {

    public PermissionDaoJPA() {
        super(Permission.class);
    }

    public Permission getUsersPermissions(IDiscussionUser user) {
        String query = "SELECT p FROM "+Permission.class.getSimpleName()+" p " +
                " WHERE " +
                " p.userId = :userId";
        Query q = em.createQuery(query);
        q.setParameter("userId", user.getId());
        List<Permission> permissions = q.getResultList();
        return permissions.isEmpty() ? null : permissions.get(0);
    }
}
