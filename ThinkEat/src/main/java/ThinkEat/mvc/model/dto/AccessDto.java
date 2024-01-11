package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Authority;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccessDto {

    private Integer id;

    private String accessName;

    private String accessFunction;

    private List<AuthorityDto> authorityList = new ArrayList<>();

}
