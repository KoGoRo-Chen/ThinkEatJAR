package ThinkEat.mvc.model.entity;

import java.util.*;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "eatrepo")
@Data
@ToString(exclude = {"eatRepo_User", "restaurant","price", "favListList", "eatRepo_TagList", "cmtList", "picList"})
public class EatRepo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "eatrepo_id")
	private Integer id;

	@Column
	private String title;
	

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	@Column(columnDefinition = "timestamp NOT NULL")
	private Date date;

	@Column
	private String article;

	//一個用戶可以發表多篇文章
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User eatRepo_User;

	//每間餐廳可以有多篇文章
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	//每篇食記可以設定一項價位資料
	@OneToOne
	@JoinColumn(name = "price_id")
	private Price price;

	//每個清單都可以收藏每篇文章，可以重複
	@ManyToMany(mappedBy = "favList_EatRepoList")
    private List<FavList> favListList = new ArrayList<>();

	//每篇文章可以擁有多個TAG標籤
	@ManyToMany(targetEntity = Tag.class)
	@JoinTable(
			name = "eatrepo_tag",
			joinColumns = {@JoinColumn(name = "eatrepo_id_ref", referencedColumnName = "eatrepo_id")},
			inverseJoinColumns = @JoinColumn(name = "tag_id_ref", referencedColumnName = "tag_id")
	)
    private List<Tag> eatRepo_TagList = new ArrayList<>();

	//一篇文章可以擁有多個留言
	@OneToMany(mappedBy = "comment_EatRepo")
    private List<Comment> cmtList = new ArrayList<>();

	//一篇文章可以擁有多張圖片
	@OneToMany(mappedBy = "pic_EatRepo")
    private List<Picture> picList = new ArrayList<>();


	
}
//@Column(nullable = false)
