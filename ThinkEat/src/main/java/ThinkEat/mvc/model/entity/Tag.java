package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"tag_EatRepoList"})
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer id;

    @Column
    private String name;

    //每個tag都可以被加入每篇文章，可重複
    @ManyToMany(mappedBy = "eatRepo_TagList")
    private List<EatRepo> tag_EatRepoList = new ArrayList<>();

}
//@Column(nullable = false)