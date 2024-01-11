package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "access")
@Data
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Integer id;

    @Column(nullable = false)
    private String accessName;

    @Column
    private String accessFunction;

    @ManyToMany(mappedBy = "accessList")
    List<Authority> authorityList = new ArrayList<>();

}
