package ThinkEat.mvc.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"pic_EatRepo"})
public class PictureDto {

    private Integer id;

    private String filename;

    private String filePath;

    private String htmlPath;

    private EatRepoDto pic_EatRepo;


}
