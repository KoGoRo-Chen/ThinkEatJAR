package ThinkEat.mvc.entity.demo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Employee {
	//用戶ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    
	//用戶名稱
	@Column
    private String userNickName;
    
	//帳號
	@Column
    private String username;
    
    //密碼
	@Column
    private String password;
	
	@OneToMany(mappedBy = "employee")
	List<Article> articles = new ArrayList<>();
}
