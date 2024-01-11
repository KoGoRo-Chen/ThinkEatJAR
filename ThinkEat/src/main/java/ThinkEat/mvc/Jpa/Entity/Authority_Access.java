package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "authority_access")
@Data
public class Authority_Access {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_ac_ref_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "authority_for_ref")
    private Authority authority_for_ref;

    @ManyToOne
    @JoinColumn(name = "access_for_ref")
    private Access access_for_ref;
}
