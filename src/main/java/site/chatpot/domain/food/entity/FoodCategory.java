package site.chatpot.domain.food.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chatpot.domain.common.entity.BaseEntity;

@Entity
@Table(name = "food_category")
@Getter
@NoArgsConstructor
public class FoodCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_category_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Builder
    public FoodCategory(String name) {
        this.name = name;
    }
}
