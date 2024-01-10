package ThinkEat.mvc.Jpa.Entity;

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
	@Column
	private Integer id;
	
	@Column(nullable = false)
	private String nickName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String userName;

	@OneToMany(mappedBy = "user") //一個用戶可發表多篇文章
	List<EatRepo> eatRepos = new ArrayList<>();

	@ManyToOne //多個用戶可以擁有相同的權限等級，但每個用戶僅能擁有一個權限
	@JoinColumn(name = "authority_id")
	private Authority authority;
}
