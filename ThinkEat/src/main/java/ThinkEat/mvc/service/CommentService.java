package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.CommentDao;
import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.model.dto.CommentDto;
import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.dto.TagDto;
import ThinkEat.mvc.model.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Integer addComment(CommentDto commentDto) {
        Date curDate = new Date();
        commentDto.setDate(curDate);
        Comment comment = modelMapper.map(commentDto, Comment.class);
        commentDao.save(comment);
        return comment.getId();
    }

    //更新留言
    @Transactional
    public Integer updateCommentById(Integer commentId, CommentDto commentDto) {
        Optional<Comment> commentOpt = commentDao.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment commentToUpdate = commentOpt.get();

            // 更新標題和日期
            commentToUpdate.setCommentContext(commentDto.getCommentContext());
            commentToUpdate.setDate(commentDto.getDate());

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
    public CommentDto getCommentById(Integer commentId) {
        Optional<Comment> commentOpt = commentDao.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
            return commentDto;
        }
        return null;
    }

    //尋找所有食紀
    public List<CommentDto> findAllComment() {
        List<Comment> commentList = commentDao.findAll();
        return commentList.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }

}




