package election.votepoll.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "voter_id"),
    @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "voter_id", unique = true, nullable = false)
    private String voterID;

    private String email;

    private String password;

    private String constituency;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_VOTER;
}
