package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Integer> {
}
