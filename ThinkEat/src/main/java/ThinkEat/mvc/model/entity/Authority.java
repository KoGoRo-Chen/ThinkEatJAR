package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userList"})
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    private String authorityName;

    //一個權限可分配給多個用戶(user)
    @OneToMany(mappedBy = "authority")
    private List<User> userList = new ArrayList<>();

    //每個權限都擁有多個功能(access)
    @ManyToMany(targetEntity = ThinkEat.mvc.model.entity.Access.class)
    @JoinTable(
            name = "authority_access",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "access_id", referencedColumnName = "id")
    )
    private List<Access> accessList = new ArrayList<>();


}
