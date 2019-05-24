package com.blogofyb.tools.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

@SuppressWarnings("unchecked")
public abstract class TypeToken<T> {
    private Class<T> rawType;
    private Type type;

    public TypeToken() {
        Type superclass = getClass().getGenericSuperclass();
        type = getType(superclass);
        rawType = getRawType(superclass);
    }

    private TypeToken(Type type) {
        this.type = getType(type);
        rawType = getRawType(type);
    }

    private Type getType(Type type) {
        if (type instanceof Class) {
            return type;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getActualTypeArguments()[0];
    }

    private Class<T> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class<T>) type;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type type1 = parameterizedType.getActualTypeArguments()[0];
        if (type1 instanceof Class) {
            return (Class<T>) type1;
        }
        return (Class<T>) ((ParameterizedType) parameterizedType.getActualTypeArguments()[0]).getRawType();
    }

    public static TypeToken get(final Type type) {
        return new TypeToken(type){};
    }

    public Class<T> getRawType() {
        return rawType;
    }

    public Type getType() {
        return type;
    }
}
