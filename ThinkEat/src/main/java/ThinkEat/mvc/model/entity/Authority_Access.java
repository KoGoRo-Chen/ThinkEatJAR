package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "authority_access")
@Data
public class Authority_Access {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority_id;

    @ManyToOne
    @JoinColumn(name = "access_id")
    private Access access_id;
}
