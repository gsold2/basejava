package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();

        Field field = r.getClass().getDeclaredField("uuid");
        field.setAccessible(true);
        System.out.println(field.get(r));
        field.set(r, "new uuid");
        System.out.println(field.get(r));

        Method method = r.getClass().getDeclaredMethod("toString");
        System.out.println(method.invoke(r));
    }
}
