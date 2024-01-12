package ThinkEat.mvc.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "restaurant")
@Data
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<EatRepo> getEatRepoList() {
		return eatRepoList;
	}

	public void setEatRepoList(List<EatRepo> eatRepoList) {
		this.eatRepoList = eatRepoList;
	}
}
//@Column(nullable = false)
