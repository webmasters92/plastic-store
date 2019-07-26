package rs.dev.plasticstore.services.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.dev.plasticstore.model.Image;
import rs.dev.plasticstore.repository.image.ImageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
@Service
public class ImageServiceImpl implements ImageService {


    @Autowired
    ImageRepository imageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveImage(Image image) {

        imageRepository.save(image);
    }

    @Override
    public Optional<Image> findImageByProductId(int id) {

        return Optional.empty();
    }

    @Override
    public void deleteImage(int id) {
        imageRepository.deleteById(id);
    }
}
