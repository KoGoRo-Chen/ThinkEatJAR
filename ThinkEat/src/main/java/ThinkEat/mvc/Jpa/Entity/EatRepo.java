package ThinkEat.mvc.Jpa.Entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	
	@JoinColumn(name = "user_id")
	@ManyToOne
	User user;
	
	@JoinColumn(name = "restaurant_id")
	@ManyToOne
	Restaurant restaurant;
	
	@OneToOne
	@JoinColumn(name = "price_id")
	Price price;
	
}
