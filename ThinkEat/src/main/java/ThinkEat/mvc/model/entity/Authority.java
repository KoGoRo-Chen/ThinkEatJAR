package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authority")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userList"})
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    //每個權限可分配給多個用戶(user)
    @ManyToMany(mappedBy = "authorities", cascade = CascadeType.DETACH)
    private List<User> userList = new ArrayList<>();

    @Override
    public String getAuthority() {
        return "Role_" + name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getUserList() {
        return userList;
    }
}
