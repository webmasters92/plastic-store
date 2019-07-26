package rs.dev.plasticstore.services.image;

import rs.dev.plasticstore.model.Image;

import java.util.Optional;

public interface ImageService {

    void saveImage(Image image);

    Optional<Image> findImageByProductId(int id);

    void deleteImage(int id);

}
