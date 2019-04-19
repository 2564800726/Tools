package com.blogofyb.tools.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class MyJson {
    private final Class[] classes = {byte.class, short.class, int.class, long.class, float.class,
            double.class, char.class, boolean.class, Byte.class, Short.class, Integer.class,
            Long.class, Float.class, Double.class, Character.class, Boolean.class};

    /**
     * 不带泛型解析
     * @param json  json字符串
     * @param tClass  目标对象的类类型
     * @param <T>  目标对象的类型
     * @return  目标对象
     */
    public <T> T fromJson(String json, Class<T> tClass) {
        try {
            T value;
            TypeToken typeToken = TypeToken.get(tClass);
            value = fromJson(json, typeToken);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 带泛型解析
     * @param json  json字符串
     * @param typeOf  泛型的信息
     * @param <T>  目标对象的类型
     * @return  目标对象
     */
    public <T> T fromJson(String json, TypeToken typeOf) {
        Class<T> tClass = typeOf.getRawType();
        try {
            T value = tClass.newInstance();
            JSONObject jsonObject = new JSONObject(json);
            for (Iterator<String> keys = jsonObject.keys(); keys.hasNext(); ) {
                String key = keys.next();
                Object object = jsonObject.get(key);
                Field field = tClass.getDeclaredField(key);
                field.setAccessible(true);
                Type fieldType = field.getGenericType();
                if (fieldType instanceof ParameterizedType) {
                    JSONArray array = (JSONArray) object;
                    object = fromJsonArray(array, TypeToken.get(fieldType));
                } else if (fieldType instanceof TypeVariable) {
                    object = fromJson(jsonObject.getString(key), TypeToken.get(typeOf.getType()));
                } else if (object instanceof JSONObject) {
                    object = fromJson(object.toString(), TypeToken.get(fieldType));
                }
                field.set(value, object);
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List fromJsonArray(JSONArray array, TypeToken typeToken) throws JSONException {
        List list = new ArrayList();
        int length = array.length();
        TypeToken typeToken1 = TypeToken.get(typeToken.getType());
        for (int i = 0; i < length; i++) {
            Object object = array.get(i);
            if (object instanceof JSONArray) {
                object = fromJsonArray((JSONArray) object, typeToken1);
            } else if (object instanceof JSONObject) {
                object = fromJson(array.get(i).toString(), typeToken1);
            }
            list.add(object);
        }
        return list;
    }

    /**
     * 将一个bean对象转换为json字符串
     * @param bean  需要转换的对象
     * @return  转换之后的字符串
     */
    public String fromObject(Object bean) {
        Class clazz = bean.getClass();
        StringBuilder builder = new StringBuilder();
        try {
            Field[] fields = clazz.getDeclaredFields();
            builder.append("{");
            for (Field field : fields) {
                field.setAccessible(true);
                String packageName = field.getType().getName();
                builder.append("\"").append(field.getName()).append("\": ");
                if (packageName.startsWith("java.util.")) {
                    builder.append(fromArray(field.get(bean)));
                } else if (packageName.equals("java.lang.String")) {
                    builder.append("\"").append(field.get(bean)).append("\"");
                } else if (isPrimitiveType(field.getType())) {
                    builder.append(field.get(bean));
                } else {
                    builder.append(fromObject(field.get(bean)));
                }
                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length() - 1);
            builder.append("}");
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将一个List转换为JSONArray字符串
     * @param array  需要转换的List
     * @return  转换之后的JSONArray字符串
     */
    private String fromArray(Object array) {
        Class clazz = array.getClass();
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("[");
            Field sizeField = clazz.getDeclaredField("size");
            sizeField.setAccessible(true);
            int size = (int) sizeField.get(array);
            Method get = clazz.getDeclaredMethod("get", int.class);
            for (int i = 0; i < size; i++) {
                Object item = get.invoke(array, i);
                String packageName = item.getClass().getName();
                if (packageName.startsWith("java.util.")) {
                    builder.append(fromArray(item));
                } else if (packageName.equals("java.lang.String")) {
                    builder.append("\"").append(item).append("\"");
                } else if (isPrimitiveType(item.getClass())) {
                    builder.append(item);
                } else {
                    builder.append(fromObject(item));
                }
                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length() - 1);
            builder.append("]");
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断一个字段是否是基本数据类型
     * @param clazz  需要判断的字段的类型的类类型
     * @return  true 是基本数据类型 | false 不是基本数据类型
     */
    private boolean isPrimitiveType(Class clazz) {
        for (Class primitiveClass : classes) {
            if (primitiveClass == clazz) {
                return true;
            }
        }
        return false;
    }
}
