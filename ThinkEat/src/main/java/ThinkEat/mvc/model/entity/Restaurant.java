package ThinkEat.mvc.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@ToString(exclude = "eatRepoList")
@AllArgsConstructor
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id")
	private Integer id;

	@Column
	private String name;

	@Column
	private String address;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<EatRepo> eatRepoList = new ArrayList<>();

	public Restaurant() {
	}

	public Restaurant(String name, String address) {
		this.name = name;
		this.address = address;
	}
}
//@Column(nullable = false)
