package ThinkEat.mvc.Jpa.Dao;

import ThinkEat.mvc.Jpa.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Integer> {
}
