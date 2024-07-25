package site.chatpot.domain.option.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chatpot.domain.common.BaseEntity;

@Entity
@Table(name = "options")
@Getter
@NoArgsConstructor
public class Option extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "selected_count", nullable = false)
    private int selectedCount;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = Boolean.FALSE;

    @Builder
    public Option(String name) {
        this.name = name;
        this.selectedCount = 0;
        this.isDefault = false;
    }

    public boolean getIsDefault() {
        return isDefault;
    }
}
