package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authority")
@Data
public class Authority {
    @Id
    @Column
    private Integer id;

    @Column(nullable = false)
    private String authorityName;

    @OneToMany(mappedBy = "authority") //一個權限可分配給多個用戶
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "authority") //一個權限擁有多個功能
    private List<Access> accesses = new ArrayList<>();


}
