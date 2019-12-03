package rs.dev.plasticstore.Utils;

import org.springframework.data.domain.Sort;

public class Sorting {

    public static Sort returnSortedOrder(String sort) {
        Sort sorted = null;
        switch(sort) {
            case "min-price":
                sorted = Sort.by("pa.product_price").ascending();
                break;
            case "max-price":
                sorted = Sort.by("pa.product_price").descending();
                break;
            case "name-asc":
                sorted = Sort.by("name").ascending();
                break;
            case "name-desc":
                sorted = Sort.by("name").descending();
                break;
        }
        return sorted;
    }
}
