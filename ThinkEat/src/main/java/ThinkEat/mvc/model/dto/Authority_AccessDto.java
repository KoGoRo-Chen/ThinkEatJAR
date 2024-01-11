package ThinkEat.mvc.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authority_AccessDto {

    private Integer id;

    private AuthorityDto authority_for_ref;

    private AccessDto access_for_ref;
}
