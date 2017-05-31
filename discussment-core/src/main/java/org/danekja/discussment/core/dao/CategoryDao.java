package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Category;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface extends GenericDao on methods for working with categories in a database
 */
public interface CategoryDao extends GenericDao<Category> {

    /**
     * Get all categories in a database.
     *
     * @return list of Category
     */
    List<Category> getCategories();
}
