package rs.dev.plasticstore.services.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.dev.plasticstore.model.Guest;
import rs.dev.plasticstore.repository.guest.GuestRepository;

import java.util.ArrayList;

@Service
public class GuestServiceImpl implements GuestService {

    @Override
    public Guest findGuestByUsername(String username) {
        Guest guest = guestRepository.findGuestByUsername(username);
        return guest;
    }

    @Override public Guest findGuestById(int id) {
        return guestRepository.findGuestById(id);
    }

    @Override
    public void save(Guest guest) {
        guestRepository.save(guest);
    }

    @Override public ArrayList<Guest> findAll() {
        return guestRepository.findAll();
    }

    @Override public void deleteGuestById(int id) {
        guestRepository.deleteById(id);
    }

    @Autowired
    GuestRepository guestRepository;
}
