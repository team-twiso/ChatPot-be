package site.chatpot.domain.food.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chatpot.domain.common.entity.BaseEntity;
import site.chatpot.domain.image.entity.Image;

@Entity
@Table(name = "foods")
@Getter
@NoArgsConstructor
public class Food extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "selected_count", nullable = false)
    private int selectedCount;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;

    @OneToOne
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Builder
    public Food(String name, FoodCategory foodCategory, Image image) {
        this.name = name;
        this.selectedCount = 0;
        this.image = image;
        this.foodCategory = foodCategory;
        this.isDefault = false;
    }

    public boolean getIsDefault() {
        return isDefault;
    }
}
