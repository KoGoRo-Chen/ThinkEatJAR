package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"eatRepoList", "commentList", "favLists"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String nickName;

    @Column
    private Integer favListCount;

    //一個用戶可發表多篇文章
    @OneToMany(mappedBy = "eatRepo_User", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<EatRepo> eatRepoList = new ArrayList<>();

    //一個用戶可發表多篇留言
    @OneToMany(mappedBy = "comment_User", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    //一個用戶可建立多筆清單
    @OneToMany(mappedBy = "favList_User", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<FavList> favLists = new ArrayList<>();

    //一個用戶可以擁有多個權限
    @ManyToMany(targetEntity = Authority.class, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )

    private List<Authority> authorities;
}