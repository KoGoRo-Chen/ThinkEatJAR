package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "favList")
@Data
public class FavList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String favListName;

    @ManyToOne
    @JoinColumn(name = "user_id") //一個用戶可建立多筆清單
    private User user;

    @ManyToMany //一個用戶可建立多個清單，每個清單可收藏多篇文章
    @JoinTable(
            name = "favList_eatRepo",
            joinColumns = @JoinColumn(name = "favList_id"),
            inverseJoinColumns = @JoinColumn(name = "eatRepo_id")
    )
    List<EatRepo> eatRepoList = new ArrayList<>();

}
