package org.nc.util;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author: rbandara
 */
public class ArrayUtilTest {
    @Test
    public void testGetCommonElements() throws Exception {
        int[] array1 = {1, 2, 4, 9};
        int[] array2 = {1, 3, 2, 8};
        int[] array3 = {1, 6, 5, 2};
        ArrayList list = new ArrayList();
        list.add(array1);
        list.add(array2);
        list.add(array3);
        final int[] commonElements = ArrayUtil.getCommonElements(list);
        final int[] expected = {1, 2};
        assertArrayEquals(expected, commonElements);

    }


}

/**
 *
 * private List<Cluster> findMaxSimilarityClusters(TreeMap<Double, Cluster> map, int N) {
 NavigableMap<Double, Cluster> reverseMap = map.descendingMap();
 List<Cluster> topClusters = new ArrayList<Cluster>(N);
 int count = 0;
 for (Map.Entry<Double, Cluster> entry : reverseMap.entrySet()) {
 topClusters.add(entry.getValue());
 count++;
 if(count == N) break;
 }
 return topClusters;
 }
 */
