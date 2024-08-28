package site.chatpot.domain.image.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.chatpot.config.S3Properties;
import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;
import site.chatpot.domain.image.entity.Image;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Operations s3Operations;
    private final S3Properties s3Properties;

    @Transactional
    public Image save(MultipartFile multipartFile, String dirName) {
        String url = uploadS3(multipartFile, dirName);
        Image image = Image.builder()
                .url(url)
                .build();
        return imageRepository.save(image);
    }

    @Transactional
    public void delete(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMON_ENTITY_NOT_FOUND, "해당 이미지를 찾을 수 없습니다."));
        imageRepository.delete(image);
        deleteS3(image.getUrl());
    }

    private String extractObject(String url) {
        int startIndex = url.indexOf("/", "https://".length());
        return url.substring(startIndex + 1);
    }

    private String uploadS3(MultipartFile multipartFile, String dirName) {
        String originalFileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + Objects.requireNonNull(originalFileName).replaceAll("\\s", "_");

        String fileName = s3Properties.folder() + "/" + dirName + "/" + uniqueFileName;
        try {
            S3Resource resource =
                    s3Operations.upload(s3Properties.bucket(), fileName, multipartFile.getInputStream(),
                                        ObjectMetadata.builder().contentType(multipartFile.getContentType()).build());
            return resource.getURL().toString();
        } catch (IOException | S3Exception e) {
            throw new ApiException(ErrorCode.COMMON_SYSTEM_ERROR, "S3 업로드 중 오류가 발생했습니다.");
        }
    }

    private void deleteS3(String url) {
        String object = extractObject(url);
        s3Operations.deleteObject(s3Properties.bucket(), object);
    }
}
