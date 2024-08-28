package site.chatpot.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import site.chatpot.domain.common.entity.BaseEntity;
import site.chatpot.domain.image.entity.Image;
import site.chatpot.domain.user.entity.enums.Gender;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String nickname;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = Boolean.FALSE;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Builder
    public User(
            String email, String password, String name, String nickname, LocalDate birthDate, Gender gender,
            Image image
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.image = image;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }
}
