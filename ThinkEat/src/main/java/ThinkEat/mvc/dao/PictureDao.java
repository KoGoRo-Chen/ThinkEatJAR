package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDao extends JpaRepository<Picture, Integer> {
}
