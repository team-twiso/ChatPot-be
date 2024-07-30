package site.chatpot.domain.recipe.entity;

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
import site.chatpot.domain.common.BaseEntity;
import site.chatpot.domain.user.entity.User;

@Entity
@Table(name = "recipes")
@Getter
@NoArgsConstructor
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String introduction;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String ingredients;

    @Column(name = "cooking_sequence", columnDefinition = "TEXT", nullable = false)
    private String cookingSequence;

    @Column(name = "is_visibility", nullable = false)
    private boolean isVisibility = Boolean.FALSE;

    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Recipe(String name, String introduction, String ingredients, String cookingSequence, User user) {
        this.name = name;
        this.introduction = introduction;
        this.ingredients = ingredients;
        this.cookingSequence = cookingSequence;
        this.views = 0;
        this.user = user;
    }
}
