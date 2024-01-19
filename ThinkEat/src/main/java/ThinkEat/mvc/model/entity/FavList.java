package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "favlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"favList_User", "favList_EatRepoList"})
public class FavList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "listId")
    private Integer listId;

    @Column
    private String name;

    //一個用戶可建立多筆清單
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User favList_User;

    //每個清單可以收藏多篇文章，可重複
    @ManyToMany(targetEntity = EatRepo.class)
    @JoinTable(
            name = "favlist_eatrepo",
            joinColumns = {@JoinColumn(name = "favlist_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "eatrepo_id", referencedColumnName = "id")
    )
    private List<EatRepo> favList_EatRepoList = new ArrayList<>();

}
//@Column(nullable = false)
