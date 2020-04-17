package org.danekja.discussment.ui.wicket.panel.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Post;

/**
 * Panel to display post and chain of its replies.
 */
public class PostPanel extends Panel {

    private IModel<Post> post;

    public PostPanel(String id, IModel<Post> model) {
        super(id, model);


    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }
}
