package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.EatRepo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PictureDto {

    private Integer id;

    private String Path;

    private EatRepoDto pic_EatRepo;


}
