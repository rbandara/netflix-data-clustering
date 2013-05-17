package org.nc.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author rbandara
 */
public class CommonUtils {

    static Logger logger = Logger.getLogger(CommonUtils.class.getName());

    public static void  writeToFile(String fileName, String filePath, Object object){
        try {
            File file = new File(filePath, fileName);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            logger.debug("saved "+ fileName + " file ..");
            fos.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getAverageOfIntList(List<Integer> values) {
        Integer sum = 0;
        for (Integer value : values) {
            sum += value;
        }
        return sum.doubleValue() / values.size();
    }

    public static double getAverageOfDoubleList(List<Double> values) {
        Double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        return sum.doubleValue() / values.size();
    }

    public static Integer sum(Collection<Integer> list) {
        Integer sum= 0;
        for (Integer i:list)
            sum = sum + i;
        return sum;
    }


}
