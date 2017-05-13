package org.danekja.discussment.ui.wicket.form.panel.category;

import org.danekja.discussment.core.service.ICategoryService;
import org.danekja.discussment.ui.wicket.form.CategoryForm;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class CategoryModalFormPanel extends Panel {

    private CategoryForm categoryForm;

    public CategoryModalFormPanel(String id, ICategoryService categoryService) {
        super(id);

        categoryForm = new CategoryForm("categoryForm", categoryService);
        add(categoryForm);

    }


}

