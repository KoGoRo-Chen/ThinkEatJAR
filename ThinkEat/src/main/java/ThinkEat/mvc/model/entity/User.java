package ThinkEat.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
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
    private String rawPassword;

    @Column
    private String nickname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(columnDefinition = "date NOT NULL")
    private Date date;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean enabled;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean tokenExpired;

    //一個用戶可發表多篇文章
    @OneToMany(mappedBy = "eatRepo_User", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<EatRepo> eatRepoList = new ArrayList<>();

    //一個用戶可發表多篇留言
    @OneToMany(mappedBy = "comment_User", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    //一個用戶可建立多筆清單
    @OneToMany(mappedBy = "favList_User", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<FavList> favLists = new ArrayList<>();

    //有多個權限，但一個用戶只可以擁有一個權限
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "authority_id")
    private Authority authority;

}