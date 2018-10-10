package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldInfo{
    public Field field;
    public Method getter;
    public Method setter;

    public FieldInfo(){}

    public FieldInfo(Field field, Method getter, Method setter) {
        this.field = field;
        this.getter = getter;
        this.setter = setter;
    }
}