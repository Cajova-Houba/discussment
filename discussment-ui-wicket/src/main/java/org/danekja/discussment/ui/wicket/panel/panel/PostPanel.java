package org.danekja.discussment.ui.wicket.panel.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.configuration.service.ConfigurationService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.panel.postReputation.PostReputationPanel;

import java.util.Map;

/**
 * Panel to display post and chain of its replies.
 */
public class PostPanel extends Panel {

    private IModel<Post> postModel;

    private PostService postService;
    private DiscussionUserService userService;
    private ConfigurationService configurationService;
    private PostReputationService postReputationService;


    private Map<Long, IDiscussionUser> postsAuthors;

    public PostPanel(String id, IModel<Post> model, PostService postService, DiscussionUserService userService, ConfigurationService configurationService, PostReputationService postReputationService, Map<Long, IDiscussionUser> postsAuthors) {
        super(id, model);
        postModel = model;
        this.postService = postService;
        this.userService = userService;
        this.configurationService = configurationService;
        this.postReputationService = postReputationService;
        this.postsAuthors = postsAuthors;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Label text = new Label("text", new PropertyModel<String>(postModel, "text")) {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                this.setVisible(!postModel.getObject().isDisabled());
            }
        };
        add(text);

        WebMarkupContainer dis = new WebMarkupContainer("disabled") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                this.setVisible(postModel.getObject().isDisabled());
            }
        };
        add(dis);

        add(new Label("username", new LoadableDetachableModel<String>() {
            protected String load() {

                if (postsAuthors.containsKey(postModel.getObject().getId())) {
                    return postsAuthors.get(postModel.getObject().getId()).getDisplayName();
                } else {
                    return getString("error.userNotFound");
                }
            }
        }));

        add(DateLabel.forDateStyle("created", new PropertyModel<>(postModel, "created"), "MS"));


        add(createReplyAjaxLink(postModel));
        add(createRemoveLink(postModel));
        add(createDisableLink(postModel));

        add(new AttributeModifier("style", "padding-left: " + postModel.getObject().getLevel() * 30 + "px"));

        add(new PostReputationPanel("postreputation", postModel, postService, postReputationService, userService));

        add(new ListView<Post>("replies", new PropertyModel<>(postModel, "replies")) {
            @Override
            protected void populateItem(ListItem<Post> listItem) {
                listItem.add(new PostPanel("reply", listItem.getModel(), postService, userService, configurationService, postReputationService, postsAuthors));
            }
        });
    }

    private AjaxLink createReplyAjaxLink(final IModel<Post> pm) {
        return new AjaxLink("reply") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                postModel.setObject(pm.getObject());

                target.appendJavaScript("$('#replyModal').modal('show');");
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                this.setVisible(pm.getObject().getLevel() < configurationService.maxReplyLevel());
            }
        };
    }

    private Link createRemoveLink(final IModel<Post> pm) {
        return new Link("remove") {
            @Override
            public void onClick() {
                try {
                    postService.removePost(pm.getObject());
                } catch (AccessDeniedException e) {
                    this.error(getString("error.accessDenied"));
                }
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                this.setVisible(!userService.isGuest() && userService.getCurrentlyLoggedUser().getDiscussionUserId().equals(pm.getObject().getUserId()));
            }
        };
    }

    private Link createDisableLink(final IModel<Post> pm) {

        Link disableLink = new AjaxFallbackLink("disable") {
            @Override
            public void onClick(AjaxRequestTarget target) {

                if (pm.getObject().isDisabled()) {
                    try{
                        postService.enablePost(pm.getObject());
                    } catch (AccessDeniedException e) {
                        this.error(getString("error.accessDenied"));
                    }
                } else {
                    try {
                        postService.disablePost(pm.getObject());
                    } catch (AccessDeniedException e) {
                        this.error(getString("error.accessDenied"));
                    }
                }
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                this.setVisible(!userService.isGuest() && userService.getCurrentlyLoggedUser().getDiscussionUserId().equals(pm.getObject().getUserId()));
            }
        };

        WebMarkupContainer span = new WebMarkupContainer("disable_icon") {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                if (pm.getObject().isDisabled()) {
                    add(new AttributeModifier("class", "glyphicon glyphicon-ok-circle"));
                } else {
                    add(new AttributeModifier("class", "glyphicon glyphicon-ban-circle"));
                }
            }
        };
        disableLink.add(span);

        return disableLink;
    }
}
