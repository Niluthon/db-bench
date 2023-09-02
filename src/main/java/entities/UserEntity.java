package entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String login;

  @Column
  private String email;

  @Column
  private String password;

  public int hashCode() {
    return Objects.hash(login, email);
  }
}
