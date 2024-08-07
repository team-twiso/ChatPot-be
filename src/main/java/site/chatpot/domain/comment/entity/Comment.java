package site.chatpot.domain.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chatpot.domain.common.entity.BaseEntity;
import site.chatpot.domain.image.entity.Image;
import site.chatpot.domain.recipe.entity.Recipe;
import site.chatpot.domain.user.entity.User;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Builder
    public Comment(String content, User user, Image image, Recipe recipe) {
        this.content = content;
        this.user = user;
        this.image = image;
        this.recipe = recipe;
    }
}
