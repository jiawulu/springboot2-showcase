package com.wuzhong.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static void mergeObject(Object dest, Object src) {

        Field[] declaredFields = src.getClass().getDeclaredFields();

        Arrays.stream(declaredFields).forEach(field -> {

            field.setAccessible(true);

            try {

                Object value = field.get(src);

                if (null != value) {

                    if ((value.getClass() == long.class && (long) value == 0)) {
                        return;
                    }
                    if (value.getClass() == int.class && (int) value == 0) {
                        return;
                    }

                    Field declaredField = dest.getClass().getDeclaredField(field.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(dest, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                //e.printStackTrace();
            }

        });

    }

    public static void mergeObjectNull(Object dest, Object src) {

        Field[] declaredFields = src.getClass().getDeclaredFields();

        Arrays.stream(declaredFields).forEach(field -> {

            field.setAccessible(true);

            try {

                Object srcValue = field.get(src);

                if (null != srcValue) {

                    if (srcValue.getClass() == long.class && (long) srcValue == 0) {
                        return;
                    }
                    if (srcValue.getClass() == int.class && (int) srcValue == 0) {
                        return;
                    }

                    Field declaredField = dest.getClass().getDeclaredField(field.getName());
                    declaredField.setAccessible(true);
                    // do copy only when src is not null and dest is null in the mean time
                    if (declaredField.get(dest) == null) {
                        declaredField.set(dest, srcValue);
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                //e.printStackTrace();
            }

        });

    }

    public static Map<String, String> map(Object obj) {

        Map<String, String> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();

        Arrays.stream(declaredFields).forEach(field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (null != value) {
                    map.put(field.getName(), getStrValue(value));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return map;

    }

    public static <T> T parse(Map<String, String> map, Class<T> clazz) {

        T dest = null;
        try {
            dest = clazz.newInstance();
        } catch (Throwable e) {
            return null;
        }

        for (String key : map.keySet()) {

            try {

                String strValue = map.get(key);
                if (null == strValue) {
                    continue;
                }

                Field declaredField = clazz.getDeclaredField(key);
                declaredField.setAccessible(true);
                declaredField.set(dest, toObject(declaredField.getType(), strValue));

            } catch (Exception e) {
                continue;
            }

        }

        return dest;

    }

    private static String getStrValue(Object value) {
        if (value instanceof Date) {
            return String.valueOf(((Date) value).getTime());
        }
        return value.toString();
    }

    public static Object toObject(Class clazz, String value) {
        if (Boolean.class == clazz || Boolean.class == clazz) {
            return Boolean.parseBoolean(value);
        }
        if (Byte.class == clazz || byte.class == clazz) {
            return Byte.parseByte(value);
        }
        if (Short.class == clazz || short.class == clazz) {
            return Short.parseShort(value);
        }
        if (Integer.class == clazz || int.class == clazz) {
            return Integer.parseInt(value);
        }
        if (Long.class == clazz || long.class == clazz) {
            return Long.parseLong(value);
        }
        if (Float.class == clazz || float.class == clazz) {
            return Float.parseFloat(value);
        }
        if (Double.class == clazz || double.class == clazz) {
            return Double.parseDouble(value);
        }
        if (Date.class == clazz) {
            return new Date(Long.parseLong(value));
        }
        return value;
    }


    public static <T> T from(Object object, Class<T> clazz) {

        try {
            Object dest = clazz.newInstance();
            mergeObjectNonAware(dest, object);
            return (T) dest;
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static <T> List<T> from(List<Object> objects,
                                   Class<T> clazz) {

        List<T> list = new ArrayList<>();

        for (Object obj : objects) {
            list.add(from(obj, clazz));
        }

        return list;
    }

    public static void mergeObjectNonAware(Object dest, Object src) {

        try {
            org.springframework.beans.BeanUtils.copyProperties(src, dest);
        } catch (Throwable e) {
            logger.error("copyProperties exception", e);
        }
    }
}
