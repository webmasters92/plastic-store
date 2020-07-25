package rs.dev.plasticstore.services.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Image;
import rs.dev.plasticstore.repository.image.ImageRepository;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    @Transactional
    @CachePut(value = "img_by_id", key = "#id")
    public void deleteImage(int id) {
        imageRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Cacheable(value = "img_by_id", key = "#id")
    public Optional<Image> findImageByProductId(int id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    @CachePut(value = "img_by_id", key = "#image")
    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    @Autowired
    ImageRepository imageRepository;
}
