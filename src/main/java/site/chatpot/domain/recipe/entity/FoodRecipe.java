package site.chatpot.domain.recipe.entity;

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
import site.chatpot.domain.common.CreateTimeEntity;
import site.chatpot.domain.food.entity.Food;

@Entity
@Table(name = "food_recipe")
@Getter
@NoArgsConstructor
public class FoodRecipe extends CreateTimeEntity {
    @EmbeddedId
    private Pk pk;

    @MapsId("foodId")
    @ManyToOne
    @JoinColumn(name = "food_id", insertable = false, updatable = false)
    private Food food;

    @MapsId("recipeId")
    @ManyToOne
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    private Recipe recipe;

    @Builder
    public FoodRecipe(Food food, Recipe recipe) {
        this.pk = new Pk(food.getId(), recipe.getId());
        this.food = food;
        this.recipe = recipe;
    }

    @Getter
    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Pk implements Serializable {
        @Column(name = "food_id")
        private Long foodId;

        @Column(name = "recipe_id")
        private Long recipeId;

        public Pk(Long foodId, Long recipeId) {
            this.foodId = foodId;
            this.recipeId = recipeId;
        }
    }
}
