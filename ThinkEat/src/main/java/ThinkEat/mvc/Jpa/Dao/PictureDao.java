package ThinkEat.mvc.Jpa.Dao;

import ThinkEat.mvc.Jpa.Entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDao extends JpaRepository<Picture, Integer> {
}
