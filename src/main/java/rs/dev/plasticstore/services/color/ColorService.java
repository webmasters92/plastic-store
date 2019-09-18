package rs.dev.plasticstore.services.color;

import rs.dev.plasticstore.model.Colors;

import java.util.List;


public interface ColorService {

    List<Colors> findAll();

    Colors findColorsById(int id);

    Colors findColorsByName(String name);
}
