package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tag")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer id;

    @Column(nullable = false)
    private String tagName;

    //每個tag都可以被加入每篇文章，可重複
    @ManyToMany(mappedBy = "eatRepo_TagList")
    Set<EatRepo> tag_EatRepoList = new LinkedHashSet<>();

}
