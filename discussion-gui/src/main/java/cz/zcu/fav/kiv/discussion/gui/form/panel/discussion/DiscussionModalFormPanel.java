package cz.zcu.fav.kiv.discussion.gui.form.panel.discussion;

import cz.zcu.fav.kiv.discussion.gui.form.DiscussionForm;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class DiscussionModalFormPanel extends Panel {


    private DiscussionForm discussionForm;

    public DiscussionModalFormPanel(String id,  long topicId) {
        super(id);

        discussionForm = new DiscussionForm("discussionForm", topicId);
        add(discussionForm);

    }


}
