package ThinkEat.mvc.dto;

import ThinkEat.mvc.Jpa.Entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EatRepoDto {

    private Integer id;

    private String eatTitle;

    private Date eatdate;

    private String article;

    private User eatRepo_User;

    private Restaurant restaurant;

    private Price price;

    private Set<FavList> favListList = new LinkedHashSet<>();

    private Set<Tag> eatRepo_TagList = new LinkedHashSet<>();

    private Set<Comment> cmtList = new LinkedHashSet<>();

    private Set<Picture> picList = new LinkedHashSet<>();

}
