package rs.dev.plasticstore.services.color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Colors;
import rs.dev.plasticstore.repository.color.ColorRepository;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {

    @Override
    @Transactional
    @Cacheable(value = "all_colors")
    public List<Colors> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Colors findColorsById(int id) {
        return colorRepository.findById(id).get();
    }

    @Override
    public Colors findColorsByName(String name) {
        return colorRepository.findColorsByName(name).get();
    }

    @Autowired
    ColorRepository colorRepository;
}
