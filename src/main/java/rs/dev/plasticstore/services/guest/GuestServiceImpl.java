package rs.dev.plasticstore.services.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.dev.plasticstore.model.Guest;
import rs.dev.plasticstore.repository.guest.GuestRepository;

@Service
public class GuestServiceImpl implements GuestService {

    @Override
    public Guest findGuestByUsername(String username) {
        Guest guest = guestRepository.findGuestByUsername(username);
        return guest;
    }

    @Override
    public void save(Guest guest) {
        guestRepository.save(guest);
    }

    @Autowired
    GuestRepository guestRepository;
}
