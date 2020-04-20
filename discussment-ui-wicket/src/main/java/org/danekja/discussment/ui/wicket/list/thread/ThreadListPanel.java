package org.danekja.discussment.ui.wicket.list.thread;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.configuration.service.ConfigurationService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.panel.panel.PostPanel;

import java.util.*;

/**
 * Created by Martin Bláha on 04.02.17.
 *
 * The class creates the panel contains threads with the posts
 */
public class ThreadListPanel extends Panel {

    private IModel<List<Post>> threadListModel;
    private IModel<Post> postModel;
    private PostService postService;
    private DiscussionUserService userService;
    private PostReputationService postReputationService;
    private ConfigurationService configurationService;

    /**
     * Constructor for creating a instance of the panel contains the threads with the posts
     *
     * @param id id of the element into which the panel is inserted
     * @param threadListModel model for getting the threads
     * @param postModel model for setting the selected post
     * @param postService instance of the post service
     * @param postReputationService instance of the post reputation service
     * @param configurationService instance of the configuration service
     */
    public ThreadListPanel(String id,
                           IModel<List<Post>> threadListModel,
                           IModel<Post> postModel,
                           PostService postService,
                           DiscussionUserService userService,
                           PostReputationService postReputationService,
                           ConfigurationService configurationService) {
        super(id);

        this.threadListModel = threadListModel;
        this.postModel = postModel;

        this.postService = postService;
        this.userService = userService;
        this.postReputationService = postReputationService;
        this.configurationService = configurationService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Map<Long, IDiscussionUser> postsAuthors = postService.getPostsAuthors(getAllPostsDfs());

        add(new ListView<Post>("threadListView", threadListModel) {
            protected void populateItem(ListItem<Post> listItem) {
                listItem.add(new PostPanel("postPanel", listItem.getModel(), postService, userService, configurationService, postReputationService,  postsAuthors));
            }
        });
    }

    /**
     * Uses DFS to traverse reply tree of each root post and transform it into the list.
     *
     * @return All posts in threadListModel, including replies, as a list.
     */
    private List<Post> getAllPostsDfs() {
        List<Post> posts = new ArrayList<>();
        Stack<Post> replyStack = new Stack<>();
        for(Post post : threadListModel.getObject()) {
            replyStack.push(post);

            while(!replyStack.isEmpty()) {
                Post p = replyStack.pop();
                posts.add(p);

                // push replies to the stack in reverse order
                // so that they're popped in correct order
                ListIterator<Post> replyIterator = p.getReplies().listIterator(p.getReplies().size());
                while(replyIterator.hasPrevious()) {
                    replyStack.push(replyIterator.previous());
                }
            }
        }

        return posts;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        this.setVisible(!threadListModel.getObject().isEmpty());
    }
}
