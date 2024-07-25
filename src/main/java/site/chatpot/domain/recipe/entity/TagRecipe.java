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
import site.chatpot.domain.tag.entity.Tag;

@Entity
@Table(name = "tag_recipe")
@Getter
@NoArgsConstructor
public class TagRecipe extends CreateTimeEntity {
    @EmbeddedId
    private Pk pk;

    @MapsId("tagId")
    @ManyToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag tag;

    @MapsId("recipeId")
    @ManyToOne
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    private Recipe recipe;

    @Builder
    public TagRecipe(Tag tag, Recipe recipe) {
        this.pk = new Pk(tag.getId(), recipe.getId());
        this.tag = tag;
        this.recipe = recipe;
    }

    @Getter
    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Pk implements Serializable {
        @Column(name = "tag_id")
        private Long tagId;

        @Column(name = "recipe_id")
        private Long recipeId;

        public Pk(Long tagId, Long recipeId) {
            this.tagId = tagId;
            this.recipeId = recipeId;
        }
    }
}
