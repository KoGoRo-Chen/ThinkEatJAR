package ThinkEat.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //一個權限可分配給多個用戶(user)
    @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> userList;

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
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
