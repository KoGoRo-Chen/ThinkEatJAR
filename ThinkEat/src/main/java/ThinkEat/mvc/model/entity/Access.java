package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "access")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"authorityList"})
public class Access {
    @ManyToMany(mappedBy = "accessList")
    List<Authority> authorityList = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Integer id;
    @Column(nullable = false)
    private String accessName;
    @Column
    private String accessFunction;

}
