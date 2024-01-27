package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.CommentDao;
import ThinkEat.mvc.model.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentDao commentDao;
    private final EatRepoService eatRepoService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(CommentDao commentDao,
                          EatRepoService eatRepoService,
                          ModelMapper modelMapper) {
        this.commentDao = commentDao;
        this.eatRepoService = eatRepoService;
        this.modelMapper = modelMapper;
    }

    //新增留言
    @Transactional
    public Integer addComment(Comment comment) {
        Date curDate = new Date();
        comment.setDate(curDate);
        commentDao.save(comment);
        return comment.getId();
    }

    //更新留言
    @Transactional
    public Integer updateCommentById(Integer commentId, String context) {
        Optional<Comment> commentOpt = commentDao.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment commentToUpdate = commentOpt.get();

            // 更新標題和日期
            commentToUpdate.setCommentContext(context);
            commentDao.save(commentToUpdate);
            return commentToUpdate.getId();
        }
        return null;
    }

    //以ID刪除留言
    @Transactional
    public void deleteCommentById(Integer commentId) {
        Optional<Comment> commentOpt = commentDao.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment commentToDelete = commentOpt.get();
            commentDao.delete(commentToDelete);
        }
    }

    //以ID尋找留言
    public Comment getCommentById(Integer commentId) {
        Optional<Comment> commentOpt = commentDao.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            return comment;
        }
        return null;
    }

    //尋找所有食紀
    public List<Comment> findAllComment() {
        List<Comment> commentList = commentDao.findAll();
        return commentList;
    }

}




