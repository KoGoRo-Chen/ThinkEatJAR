package ThinkEat.mvc.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column
	private String nickName;

	@Column
	private String password;

	@Column
	private String userName;

	//一個用戶可發表多篇文章
	@OneToMany(mappedBy = "eatRepo_User")
	List<EatRepo> eatRepoList = new ArrayList<>();

	//一個用戶可發表多篇留言
	@OneToMany(mappedBy = "comment_User")
	List<Comment> commentList = new ArrayList<>();

	//多個用戶可以擁有相同的權限等級，但每個用戶僅能擁有一個權限
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authority_id")
	private Authority authority;

	//一個用戶可建立多筆清單
	@OneToMany(mappedBy = "favList_User")
	List<FavList> favLists = new ArrayList<>();

}
//@Column(nullable = false)