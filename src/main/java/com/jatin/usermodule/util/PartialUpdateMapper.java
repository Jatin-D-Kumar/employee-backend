package com.jatin.usermodule.util;

import java.lang.reflect.Field;

public class PartialUpdateMapper {

    /**
     * Copies non-null and non-blank values from source to target object.
     * Only works if source and target share the same field names.
     *
     * @param source The object with updated data (e.g., DTO)
     * @param target The original object to be updated (e.g., Entity)
     */
    public static <S, T> void copyNonNullProperties(S source, T target) {
        if (source == null || target == null)
            return;

        Field[] fields = source.getClass().getDeclaredFields();

        for (Field sourceField : fields) {
            sourceField.setAccessible(true);
            try {
                Object value = sourceField.get(source);
                if (value != null && !(value instanceof String && ((String) value).isBlank())) {
                    Field targetField;
                    try {
                        targetField = target.getClass().getDeclaredField(sourceField.getName());
                        targetField.setAccessible(true);
                        targetField.set(target, value);
                    } catch (NoSuchFieldException e) {
                        // Field does not exist in target — skip
                    }
                }
            } catch (IllegalAccessException ignored) {
            }
        }
    }
}
