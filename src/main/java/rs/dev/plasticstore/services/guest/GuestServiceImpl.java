package rs.dev.plasticstore.services.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Guest;
import rs.dev.plasticstore.repository.guest.GuestRepository;

import java.util.ArrayList;

@Service
public class GuestServiceImpl implements GuestService {

    @Override
    @Transactional
    @CacheEvict(value = "guest_by_id", key = "#id")
    public void deleteGuestById(int id) {
        guestRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Cacheable(value = "all_guests")
    public ArrayList<Guest> findAll() {
        return guestRepository.findAll();
    }

    @Override
    @Transactional
    public Guest findGuestById(int id) {
        return guestRepository.findGuestById(id);
    }

    @Override
    @Transactional
    public Guest findGuestByUsername(String username) {
        return guestRepository.findGuestByUsername(username);
    }

    @Override
    @Transactional
    @CachePut(value = "guest_by_id", key = "#guest")
    public void save(Guest guest) {
        guestRepository.save(guest);
    }

    @Autowired
    GuestRepository guestRepository;
}
