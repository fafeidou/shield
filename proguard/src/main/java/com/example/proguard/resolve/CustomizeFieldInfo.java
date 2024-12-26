package com.example.proguard.resolve;

public class CustomizeFieldInfo {
    public final String originalClassName;
    public final String originalType;
    public final String originalName;


    /**
     * Creates a new FieldInfo with the given properties.
     */
    public CustomizeFieldInfo(String originalClassName,
                              String originalType,
                              String originalName) {
        this.originalClassName = originalClassName;
        this.originalType = originalType;
        this.originalName = originalName;
    }


    /**
     * Returns whether the given type matches the original type of this field.
     * The given type may be a null wildcard.
     */
    public boolean matches(String originalType) {
        return originalType == null || originalType.equals(this.originalType);
    }
}
