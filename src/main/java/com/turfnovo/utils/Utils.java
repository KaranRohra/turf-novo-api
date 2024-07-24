package com.turfnovo.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

public class Utils {
    public static boolean isValidPhoneNumber(String phoneNo) {
        for (char c : phoneNo.toCharArray()) {
            if (c < '0' || c > '9')
                return false;
        }

        return true;
    }

    public static void copyNonNullProperties(Object source, Object target) {
        PropertyDescriptor[] srcPropertyDescriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        PropertyDescriptor[] tarPropertyDescriptors = BeanUtils.getPropertyDescriptors(target.getClass());

        for (PropertyDescriptor srcProp : srcPropertyDescriptors) {
            Method readMethod = srcProp.getReadMethod();
            for (PropertyDescriptor tarProp : tarPropertyDescriptors) {
                if (srcProp.getName().equals(tarProp.getName())) {
                    Method writeMethod = tarProp.getWriteMethod();
                    if (readMethod != null && writeMethod != null) {
                        try {
                            Object value = readMethod.invoke(source);
                            if (value != null) {
                                writeMethod.invoke(target, value);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("Could not copy properties", e);
                        }
                    }
                    break;
                }
            }
        }
    }
}
