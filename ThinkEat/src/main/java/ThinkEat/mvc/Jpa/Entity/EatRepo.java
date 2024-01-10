package ThinkEat.mvc.Jpa.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "eatrepo")
@Data
@ToString(exclude = {"user","restaurant"})
public class EatRepo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@Column(nullable = false)
	private String eatTitle;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	@Column(columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Date eatdate;
	
	@Column(nullable = false)
	private String eatrepo;

	@ManyToOne
	@JoinColumn(name = "user_id") //一個用戶可以發表多篇文章
	User user;

	@ManyToOne
	@JoinColumn(name = "restaurant_id") //每間餐廳可以有多篇文章
	Restaurant restaurant;
	
	@OneToOne
	@JoinColumn(name = "price_id") //每篇食記可以設定一項價位資料
	Price price;

	//每個清單都可以收藏每篇文章，可以重複
	@ManyToMany(mappedBy = "eatRepoList")
	List<FavList> favLists = new ArrayList<>();
	
}
