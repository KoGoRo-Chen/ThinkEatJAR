package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.EatRepo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"pic_EatRepo"})
public class PictureDto {

    private Integer id;

    private String Path;

    private EatRepoDto pic_EatRepo;


}
