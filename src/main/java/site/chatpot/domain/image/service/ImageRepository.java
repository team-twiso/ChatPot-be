package site.chatpot.domain.image.service;

import org.springframework.data.jpa.repository.JpaRepository;
import site.chatpot.domain.image.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
