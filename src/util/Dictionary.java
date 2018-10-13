package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    public Map<String, Object> memberValuesDictionary(Object o) throws IllegalAccessException {
        Class aclass = o.getClass();
        Field[] fields = aclass.getDeclaredFields();
        Map<String, Object> newMap = new HashMap<>();

        for (int i = 0; i < fields.length; i++)
            newMap.put(fields[i].getName(), fields[i].get(o));

        return newMap;
    }

    public static String getterName(String fieldName) {
        StringBuilder builder = new StringBuilder(fieldName.length() + 3);
        builder.append("get");
        builder.append(fieldName);
        builder.setCharAt(3, Character.toUpperCase(builder.charAt(3)));
        return builder.toString();
    }

    public static String setterName(String fieldName) {
        StringBuilder builder = new StringBuilder(fieldName.length() + 3);
        builder.append("set");
        builder.append(fieldName);
        builder.setCharAt(3, Character.toUpperCase(builder.charAt(3)));
        return builder.toString();
    }

    public static Method getter(Field field) throws NoSuchMethodException {
        String fieldName = getterName(field.getName());
        return field.getDeclaringClass().getMethod(fieldName);
    }

    public static Method setter(Field field) throws NoSuchMethodException {
        String fieldName = setterName(field.getName());
        return field.getDeclaringClass().getMethod(fieldName, field.getType());
    }

    public static Map<String, FieldInfo> memberInfoDictionary(Class c) {
        if (c == null)
            return new HashMap<>();

        Field[] fields = c.getDeclaredFields();
        Map<String, FieldInfo> newMap = new HashMap<>();
        for (Field f : fields) {
            FieldInfo info = new FieldInfo();
            info.field = f;
            try {
                info.getter = getter(f);
            } catch (NoSuchMethodException e) {
                info.getter = null;
            }
            try {
                info.setter = setter(f);
            } catch (NoSuchMethodException e) {
                info.setter = null;
            }
            newMap.put(f.getName(), info);
        }
        newMap.putAll(memberInfoDictionary(c.getSuperclass()));
        return newMap;
    }
}
