package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authority")
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
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
            joinColumns = {@JoinColumn(name = "authority_for_ref", referencedColumnName = "authority_id")},
            inverseJoinColumns = @JoinColumn(name = "access_for_ref", referencedColumnName = "access_id")
    )
    List<Access> accessList = new ArrayList<>();


}
