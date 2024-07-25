package site.chatpot.domain.tag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chatpot.domain.common.CreateTimeEntity;

@Entity
@Table(name = "tags")
@Getter
@NoArgsConstructor
public class Tag extends CreateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
