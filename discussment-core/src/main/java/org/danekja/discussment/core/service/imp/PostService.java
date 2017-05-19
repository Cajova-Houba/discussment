package org.danekja.discussment.core.service.imp;


import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import java.util.List;

/**
 * Created by Martin Bláha on 07.02.17.
 */
public class PostService implements org.danekja.discussment.core.service.PostService {

    private PostDao postDao;

    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public void removePost(Post post) {

        if (post.getPost() != null) {
            post.getPost().getReplies().remove(post);
        } else {
            post.getDiscussion().getPosts().remove(post);
        }
        postDao.remove(post);
    }

    public Post getPostById(long postId) {
        return postDao.getById(postId);
    }

    public Post sendReply(Post reply, Post IPost) {

        int level = IPost.getLevel();
        reply.setLevel(++level);

        IPost.addReply(reply);

        return postDao.save(reply);

    }

    public Post sendPost(Discussion discussion, Post post) {

        discussion.addPost(post);

        return postDao.save(post);
    }

    public Post disablePost(Post post) {

        post.setDisabled(true);

        return postDao.save(post);

    }

    public Post enablePost(Post post) {

        post.setDisabled(false);

        return postDao.save(post);

    }

    public List<Post> listPostHierarchy(Discussion discussion) {
        //implement this so that single database query is used
        //this is hard to achieve with SQL, but necessary performance-wise;
        //easiest solution is to fetch posts belonging to discussion in a list
        //and building the tree hierarchy in code
        //throw new UnsupportedOperationException("To be implemented");
        return postDao.getPostsByDiscussion(discussion);
    }
}
