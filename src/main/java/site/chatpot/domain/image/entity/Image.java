package site.chatpot.domain.image.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chatpot.domain.common.CreateTimeEntity;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor
public class Image extends CreateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(nullable = false)
    private String url;

    @Builder
    public Image(String url) {
        this.url = url;
    }
}
