package rs.dev.plasticstore.services.image;

import rs.dev.plasticstore.model.Image;

import java.util.Optional;

public interface ImageService {

    void deleteImage(int id);

    Optional<Image> findImageByProductId(int id);

    void saveImage(Image image);

}
