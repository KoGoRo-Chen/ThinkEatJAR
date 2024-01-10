package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Entity
@Table(name = "favList")
@Data
public class FavList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favlist_id")
    private Integer id;

    @Column(nullable = false)
    private String favListName;

    //一個用戶可建立多筆清單
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //一個用戶可建立多個清單，每個清單可收藏多篇文章
    @ManyToMany(targetEntity = EatRepo.class)
    @JoinTable(
            name = "favlist_eatrepo",
            joinColumns = {@JoinColumn(name = "favList_id_ref", referencedColumnName = "favlist_id")},
            inverseJoinColumns = @JoinColumn(name = "eatrepo_id_ref", referencedColumnName = "eatrepo_id")
    )
    LinkedHashSet<EatRepo> eatRepoList = new LinkedHashSet<>();

}
