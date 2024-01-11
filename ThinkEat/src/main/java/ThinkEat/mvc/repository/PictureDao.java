package ThinkEat.mvc.repository;

import ThinkEat.mvc.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDao extends JpaRepository<Picture, Integer> {
}
