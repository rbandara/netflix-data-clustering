package org.nc.data;

import org.junit.Before;
import org.junit.Test;
import org.nc.util.Constants;

import static junit.framework.Assert.assertEquals;

/**
 * User: rbandara
 * Date: 2/13/13
 * Time: 5:28 PM
 */
public class CustomerIdMapperTest {
    CustomerIdMapper mapper = null;

    @Before
    public void setUp() {
        mapper = new CustomerIdMapper();
    }

    @Test
    public void testLoadCustomerId() {
        assertEquals(6, mapper.getCustomerId(0));
        assertEquals(7, mapper.getCustomerId(1));
        assertEquals(8, mapper.getCustomerId(2));
        assertEquals(10, mapper.getCustomerId(3));
        assertEquals(387418, mapper.getCustomerId(70466));
        assertEquals(2649429, mapper.getCustomerId(Constants.NO_OF_USERS - 1));
    }

    @Test
    public void testLoadMapperId() {
        assertEquals(0, mapper.getMappedId(6));
        assertEquals(1, mapper.getMappedId(7));
        assertEquals(2, mapper.getMappedId(8));
        assertEquals(3, mapper.getMappedId(10));
        assertEquals(Constants.NO_OF_USERS - 1, mapper.getMappedId(2649429));
    }
}
