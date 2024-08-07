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
import site.chatpot.domain.common.entity.CreateTimeEntity;
import site.chatpot.domain.option.entity.Option;

@Entity
@Table(name = "option_recipe")
@Getter
@NoArgsConstructor
public class OptionRecipe extends CreateTimeEntity {
    @EmbeddedId
    private Pk pk;

    @MapsId("optionId")
    @ManyToOne
    @JoinColumn(name = "option_id", insertable = false, updatable = false)
    private Option option;

    @MapsId("recipeId")
    @ManyToOne
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    private Recipe recipe;

    @Builder
    public OptionRecipe(Option option, Recipe recipe) {
        this.pk = new Pk(option.getId(), recipe.getId());
        this.option = option;
        this.recipe = recipe;
    }

    @Getter
    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Pk implements Serializable {
        @Column(name = "option_id")
        private Long optionId;

        @Column(name = "recipe_id")
        private Long recipeId;

        public Pk(Long optionId, Long recipeId) {
            this.optionId = optionId;
            this.recipeId = recipeId;
        }
    }


}
