package ThinkEat.mvc.Jpa.Entity;

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

	@Column(nullable = false)
	private String eatTitle;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	@Column(columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Date eatdate;
	
	@Column(nullable = false)
	private String article;

	//一個用戶可以發表多篇文章
	@ManyToOne
	@JoinColumn(name = "user_id")
	User eatRepo_User;

	//每間餐廳可以有多篇文章
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	Restaurant restaurant;

	//每篇食記可以設定一項價位資料
	@OneToOne
	@JoinColumn(name = "price_id")
	Price price;

	//每個清單都可以收藏每篇文章，可以重複
	@ManyToMany(mappedBy = "favList_EatRepoList")
	Set<FavList> favListList = new LinkedHashSet<>();

	//每篇文章可以擁有多個TAG標籤
	@ManyToMany(targetEntity = Tag.class)
	@JoinTable(
			name = "eatrepo_tag",
			joinColumns = {@JoinColumn(name = "eatrepo_id_ref", referencedColumnName = "eatrepo_id")},
			inverseJoinColumns = @JoinColumn(name = "tag_id_ref", referencedColumnName = "tag_id")
	)
	Set<Tag> eatRepo_TagList = new LinkedHashSet<>();

	//一篇文章可以擁有多個留言
	@OneToMany(mappedBy = "comment_EatRepo")
	Set<Comment> cmtList = new LinkedHashSet<>();

	//一篇文章可以擁有多張圖片
	@OneToMany(mappedBy = "pic_EatRepo")
	Set<Picture> picList = new LinkedHashSet<>();


	
}
