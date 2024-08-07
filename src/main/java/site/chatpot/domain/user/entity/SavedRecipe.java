package site.chatpot.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chatpot.domain.common.entity.CreateTimeEntity;
import site.chatpot.domain.recipe.entity.Recipe;

@Entity
@Table(name = "saved_recipe")
@Getter
@NoArgsConstructor
public class SavedRecipe extends CreateTimeEntity {
    @EmbeddedId
    private Pk pk;

    @MapsId("recipeId")
    @ManyToOne
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    private Recipe recipe;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Builder
    public SavedRecipe(User user, Recipe recipe) {
        this.pk = new Pk(user.getId(), recipe.getId());
        this.recipe = recipe;
        this.user = user;
    }

    @Getter
    @NoArgsConstructor
    @Embeddable
    @EqualsAndHashCode
    public static class Pk implements Serializable {
        @Column(name = "user_id")
        private Long userId;
        @Column(name = "recipe_id")
        private Long recipeId;

        public Pk(Long userId, Long recipeId) {
            this.userId = userId;
            this.recipeId = recipeId;
        }
    }
}
