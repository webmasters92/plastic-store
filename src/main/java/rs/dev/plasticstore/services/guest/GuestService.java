package rs.dev.plasticstore.services.guest;

import rs.dev.plasticstore.model.Guest;

import java.util.ArrayList;

public interface GuestService {

    Guest findGuestByUsername(String username);

    Guest findGuestById(int id);

    void save(Guest guest);

    ArrayList<Guest> findAll();

    void deleteGuestById(int id);
}
