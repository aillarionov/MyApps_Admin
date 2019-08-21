package ru.gastroafisha.myappsadmin;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author alex
 */
public class Utils {

    public static <T> void moveItem(int sourceIndex, int targetIndex, List<T> list) {
        if (sourceIndex <= targetIndex) {
            Collections.rotate(list.subList(sourceIndex, targetIndex + 1), -1);
        } else {
            Collections.rotate(list.subList(targetIndex, sourceIndex + 1), 1);
        }
    }
}
