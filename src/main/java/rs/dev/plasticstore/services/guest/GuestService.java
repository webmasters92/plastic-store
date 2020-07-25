package rs.dev.plasticstore.services.guest;

import rs.dev.plasticstore.model.Guest;

import java.util.ArrayList;

public interface GuestService {

    void deleteGuestById(int id);

    ArrayList<Guest> findAll();

    Guest findGuestById(int id);

    Guest findGuestByUsername(String username);

    void save(Guest guest);
}
