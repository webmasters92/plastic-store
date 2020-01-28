package rs.dev.plasticstore.services.guest;

import rs.dev.plasticstore.model.Guest;

public interface GuestService {

    Guest findGuestByUsername(String username);

    void save(Guest guest);
}
