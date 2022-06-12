package com.Endless.utilities;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NMSUtilities {
    private static Class<?> chatserial;

    public enum MinecraftVersion {
        V1_8_R1, V1_8_R2, V1_8_R3, V1_9_R1, V1_9_R2, V1_10_R1, V1_11_R1, V1_12_R1, V1_13_R1, V1_14_R1, V1_15_R1, V1_16_R1, V1_17_R1, V1_18_R1, V1_19_R1;
    }

    static {
        if (compareMCVersionHigherOrEqual("1.8.3")) {
            chatserial = getNMSClass("IChatBaseComponent$ChatSerializer");
        } else if (compareMCVersionHigherOrEqual("1.8")) {
            chatserial = getNMSClass("ChatSerializer");
        } else if (compareMCVersionHigherOrEqual("1.7.10")) {
            chatserial = getNMSClass("ChatSerializer");
        }
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object nmsPlayer = getNMSPlayer(player);
            Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
            connection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(connection, new Object[] { packet });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket(Player player, String packetName, Class<?>[] parameterclass, Object... parameters) {
        try {
            Object nmsPlayer = getNMSPlayer(player);
            Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
            Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + "." + packetName).getConstructor(parameterclass).newInstance(parameters);
            connection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(connection, new Object[] { packet });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Class<?> getCBClass(String className) {
        String fullName = Bukkit.getServer().getClass().getPackage().getName() + "." + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Class<?> getProtocolInjectorClass(String className) {
        String fullName = "org.spigotmc.ProtocolInjector$" + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Object getNMSPlayer(Player player) {
        try {
            return player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... params) {
        try {
            return clazz.getConstructor(params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Field field) {
        field.setAccessible(true);
        return field;
    }

    public static Field getFieldByType(Class<?> clazz, Class<?> type, int index) {
        int curIndex = 0;
        Field[] arrayOfField;
        int j = (arrayOfField = clazz.getFields()).length;
        for (int i = 0; i < j; i++) {
            Field field = arrayOfField[i];
            if (field.getType() == type) {
                if (curIndex == index)
                    return field;
                curIndex++;
            }
        }
        return null;
    }

    public static Object getFieldValue(Object object, Field field) {
        Validate.isTrue((object != null || Modifier.isStatic(field.getModifiers())), "object cannot be null");
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setFieldValue(Object object, Field field, Object newValue) {
        Validate.isTrue((object != null || Modifier.isStatic(field.getModifiers())));
        try {
            field.set(object, newValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldValue(Object object, String field, Object newValue) {
        Validate.isTrue((object != null));
        try {
            Field ffield = object.getClass().getDeclaredField(field);
            ffield.setAccessible(true);
            ffield.set(object, newValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Field getDeclaredFieldByType(Class<?> clazz, Class<?> type, int index) {
        return getDeclaredFieldByType(clazz, type, index, false);
    }

    public static Field getDeclaredFieldByType(Class<?> clazz, Class<?> type, int index, boolean accessible) {
        Validate.isTrue((index >= 0), "index cannot be less than zero");
        int curIndex = 0;
        Field[] arrayOfField;
        int j = (arrayOfField = clazz.getDeclaredFields()).length;
        for (int i = 0; i < j; i++) {
            Field field = arrayOfField[i];
            if (field.getType() == type) {
                if (curIndex == index) {
                    field.setAccessible(true);
                    return field;
                }
                curIndex++;
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            return clazz.getMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getMethodByType(Class<?> clazz, Class<?> type, int index) {
        return getMethodByPredicate(clazz, (new MethodPredicate()).withReturnType(type), index);
    }

    public static Method getMethodByPredicate(Class<?> clazz, Predicate<Method> predicate, int index) {
        Validate.isTrue((index >= 0), "index cannot be less than zero");
        int curIndex = 0;
        Method[] arrayOfMethod;
        int j = (arrayOfMethod = clazz.getMethods()).length;
        for (int i = 0; i < j; i++) {
            Method method = arrayOfMethod[i];
            if (predicate == null || predicate.test(method)) {
                if (curIndex == index)
                    return method;
                curIndex++;
            }
        }
        return null;
    }

    public static Method getDeclaredMethodByType(Class<?> clazz, Class<?> type, int index) {
        return getDeclaredMethodByType(clazz, type, index, false);
    }

    public static Method getDeclaredMethodByType(Class<?> clazz, Class<?> type, int index, boolean accessible) {
        return getDeclaredMethodByPredicate(clazz, (new MethodPredicate()).withReturnType(type), 0, accessible);
    }

    public static Method getDeclaredMethodByPredicate(Class<?> clazz, Predicate<Method> predicate, int index, boolean accessible) {
        Validate.isTrue((index >= 0), "index cannot be less than zero");
        int curIndex = 0;
        Method[] arrayOfMethod;
        int j = (arrayOfMethod = clazz.getDeclaredMethods()).length;
        for (int i = 0; i < j; i++) {
            Method method = arrayOfMethod[i];
            if (!predicate.test(method)) {
                if (curIndex == index) {
                    method.setAccessible(accessible);
                    return method;
                }
                curIndex++;
            }
        }
        return null;
    }

    public static Object invokeMethod(Object object, Method method, Object... params) {
        Validate.isTrue((object != null || Modifier.isStatic(method.getModifiers())), "object cannot be null");
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeConstructor(Constructor<?> constructor, Object... params) {
        try {
            return constructor.newInstance(params);
        } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getEnumConstant(Class<?> clazz, String constant) {
        try {
            Field field = clazz.getField(constant);
            return field.get(null);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getClass(String clazz) {
        Validate.notNull(clazz, "clazz cannot be null");
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1) + ".";
    }

    public static boolean compareMCVersionHigherOrEqual(String version) {
        String serverVersion = Bukkit.getVersion();
        serverVersion = serverVersion.substring(serverVersion.indexOf("(MC: ") + 5, serverVersion.length());
        serverVersion = serverVersion.substring(0, serverVersion.lastIndexOf(")"));
        String[] serverVersionArray = serverVersion.split("\\.");
        String[] toCompareVersionArray = version.split("\\.");
        if (serverVersionArray.length == 2) {
            int serverFirst = Integer.valueOf(serverVersionArray[0]).intValue();
            int toCompareFirst = Integer.valueOf(toCompareVersionArray[0]).intValue();
            if (toCompareFirst != serverFirst)
                return (toCompareFirst < serverFirst);
            int serverSecond = Integer.valueOf(serverVersionArray[1]).intValue();
            int toCompareSecond = Integer.valueOf(toCompareVersionArray[1]).intValue();
            if (toCompareSecond != serverSecond)
                return (toCompareSecond < serverSecond);
            if (toCompareVersionArray.length == 3)
                return false;
            return true;
        }
        if (serverVersionArray.length == 3) {
            int serverFirst = Integer.valueOf(serverVersionArray[0]).intValue();
            int toCompareFirst = Integer.valueOf(toCompareVersionArray[0]).intValue();
            if (toCompareFirst != serverFirst)
                return (toCompareFirst < serverFirst);
            int serverSecond = Integer.valueOf(serverVersionArray[1]).intValue();
            int toCompareSecond = Integer.valueOf(toCompareVersionArray[1]).intValue();
            if (toCompareSecond != serverSecond)
                return (toCompareSecond < serverSecond);
            if (toCompareVersionArray.length != 3)
                return true;
            int serverThird = Integer.valueOf(serverVersionArray[2]).intValue();
            int toCompareThird = Integer.valueOf(toCompareVersionArray[2]).intValue();
            if (toCompareThird != serverThird)
                return (toCompareThird < serverThird);
            return true;
        }
        return false;
    }

    public static Class<?> getChatSerial() {
        return chatserial;
    }

    public static class MethodPredicate implements Predicate<Method> {
        private Class<?> returnType;

        private Class<?>[] params;

        private List<Integer> withModifiers;

        private List<Integer> withoutModifiers;

        private Predicate<Method> predicate;

        private String name;

        public MethodPredicate withReturnType(Class<?> returnType) {
            this.returnType = returnType;
            return this;
        }

        public MethodPredicate withParams(Class<?>... params) {
            this.params = params;
            return this;
        }

        public MethodPredicate withModifiers(int... modifiers) {
            this.withModifiers = (List<Integer>) Arrays.stream(modifiers).boxed().collect((Collector)Collectors.toList());
            return this;
        }

        public MethodPredicate withModifiers(Collection<Integer> modifiers) {
            this.withModifiers = new ArrayList<>(modifiers);
            return this;
        }

        public MethodPredicate withoutModifiers(int... modifiers) {
            this.withoutModifiers = (List<Integer>) Arrays.stream(modifiers).boxed().collect((Collector)Collectors.toList());
            return this;
        }

        public MethodPredicate withoutModifiers(Collection<Integer> modifiers) {
            this.withoutModifiers = new ArrayList<>(modifiers);
            return this;
        }

        public MethodPredicate withPredicate(Predicate<Method> predicate) {
            this.predicate = predicate;
            return this;
        }

        public MethodPredicate withName(String name) {
            this.name = name;
            return this;
        }

        public boolean test(Method method) {
            if (this.returnType != null && method.getReturnType() != this.returnType)
                return false;
            if (this.params != null && !Arrays.equals((Object[])method.getParameterTypes(), (Object[])this.params))
                return false;
            if (this.withModifiers != null) {
                int modifiers = method.getModifiers();
                for (Iterator<Integer> localIterator = this.withModifiers.iterator(); localIterator.hasNext(); ) {
                    int bitMask = ((Integer)localIterator.next()).intValue();
                    if ((modifiers & bitMask) == 0)
                        return false;
                }
            }
            if (this.withoutModifiers != null) {
                int modifiers = method.getModifiers();
                for (Iterator<Integer> localIterator = this.withoutModifiers.iterator(); localIterator.hasNext(); ) {
                    int bitMask = ((Integer)localIterator.next()).intValue();
                    if ((modifiers & bitMask) != 0)
                        return false;
                }
            }
            if (this.predicate != null && !this.predicate.test(method))
                return false;
            if (this.name != null && !method.getName().equals(this.name))
                return false;
            return true;
        }

        public String toString() {
            List<String> args = Lists.newArrayList();
            if (this.returnType != null)
                args.add("return type " + this.returnType);
            if (this.params != null)
                args.add("params " + Arrays.toString((Object[])this.params));
            if (this.withModifiers != null)
                args.add("with modifiers (bitmasks) " + this.withModifiers);
            if (this.withoutModifiers != null)
                args.add("without modifiers (bitmasks) " + this.withoutModifiers);
            if (this.predicate != null)
                args.add("specified predicate");
            if (this.name != null)
                args.add("with name " + this.name);
            return Joiner.on(", ").join(args.subList(0, args.size() - 1)) + ", and " + (String)args.get(args.size() - 1);
        }
    }
}

