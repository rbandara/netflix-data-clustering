package org.nc.util;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Contains common array related operations
 */
public class ArrayUtil {

    /**
     * @param intArrayList a list of arrays
     * @return common elements in those arrays
     */
    public static int[] getCommonElements(ArrayList<int[]> intArrayList) {

        ArrayList commonElementList = new ArrayList();

        // create a google guava Multiset
        Multiset<Integer> intsMultiset = HashMultiset.create();
        for (int[] intArray : intArrayList) {
            intsMultiset.addAll(Ints.asList(intArray));
        }
        final Set<Integer> elements = intsMultiset.elementSet();
        final Iterator<Integer> iterator = elements.iterator();
        while (iterator.hasNext()) {
            Integer intElement = iterator.next();
            // count the occurances of intElement
            final int count = intsMultiset.count(intElement);
            // see if the element is present in all the lists
            if (count == intArrayList.size()) {
                commonElementList.add(intElement);
            }
        }
        return Ints.toArray(commonElementList);
    }


}
