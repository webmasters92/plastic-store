package rs.dev.plasticstore.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinMax {

    public static Integer findMax(List<Integer> list) {
        if(list == null || list.size() == 0) return Integer.MIN_VALUE;

        List<Integer> sortedlist = new ArrayList<>(list);

        Collections.sort(sortedlist);

        return sortedlist.get(sortedlist.size() - 1);
    }

    public static Integer findMin(List<Integer> list) {
        if(list == null || list.size() == 0) return Integer.MAX_VALUE;
        List<Integer> sortedlist = new ArrayList<>(list);

        Collections.sort(sortedlist);

        return sortedlist.get(0);
    }
}
