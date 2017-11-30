package org.danekja.discussment.example.page.discussion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.dao.NewPermissionDao;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.jpa.CategoryDaoJPA;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.dao.jpa.TopicDaoJPA;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.core.service.imp.DefaultCategoryService;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.danekja.discussment.core.service.imp.DefaultTopicService;
import org.danekja.discussment.core.service.imp.NewDiscussionService;
import org.danekja.discussment.example.core.DefaultUserService;
import org.danekja.discussment.example.core.UserDao;
import org.danekja.discussment.example.core.UserDaoMock;
import org.danekja.discussment.example.core.UserService;
import org.danekja.discussment.example.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.forum.ForumPanel;

import java.util.HashMap;


/**
 * Homepage
 */
public class DiscussionPage extends BasePage {

	private static final long serialVersionUID = 1L;

    private DiscussionService discussionService;
    private CategoryService categoryService;
    private TopicService topicService;
    private PostService postService;
    private UserService userService;
    private AccessControlService accessControlService;

    private IModel<HashMap<String, Integer>> parametersModel;

    final PageParameters parameters;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public DiscussionPage(final PageParameters parameters) {

        this.parameters = parameters;

        CategoryDaoJPA categoryDaoJPA = new CategoryDaoJPA();
        TopicDaoJPA topicJPA = new TopicDaoJPA();
        UserDao userDao = new UserDaoMock();
        DiscussionDaoJPA discussionJPA = new DiscussionDaoJPA();
        NewPermissionDao permissionJPA = new PermissionDaoJPA();
        PostDaoJPA postJPA = new PostDaoJPA();

        this.userService = new DefaultUserService(userDao, permissionJPA);
        this.accessControlService = new PermissionService(permissionJPA, userService);
        this.discussionService = new NewDiscussionService(discussionJPA, accessControlService, userService);
        this.categoryService = new DefaultCategoryService(categoryDaoJPA);
        this.topicService = new DefaultTopicService(topicJPA, categoryDaoJPA);
        this.postService = new DefaultPostService(postJPA, userService);

        parametersModel = new Model<HashMap<String, Integer>>();
        parametersModel.setObject(new HashMap<String, Integer>());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        parametersModel.getObject().put("topicId", parameters.get("topicId").isNull() ? -1 : Integer.parseInt(parameters.get("topicId").toString()));
        parametersModel.getObject().put("discussionId", parameters.get("discussionId").isNull() ? -1 : Integer.parseInt(parameters.get("discussionId").toString()));

        add(new ForumPanel("content", parametersModel, discussionService, topicService, categoryService, postService, accessControlService));
    }

    @Override
    public String getTitle() {
        return "Post page";
    }
}

