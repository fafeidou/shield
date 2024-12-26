package com.example.proguard.resolve;

public class CustomizeMethodInfo {
    /**
     * 混淆首行行号
     */
    public final int obfuscatedFirstLineNumber;

    /**
     * 混淆末行行号
     */
    public final int obfuscatedLastLineNumber;

    /**
     * 原始类型
     */
    public final String originalClassName;

    /**
     * 原始首行行号
     */
    public final int originalFirstLineNumber;

    /**
     * 原始末行行号
     */
    public final int originalLastLineNumber;

    /**
     * 原始类型
     */
    public final String originalType;

    /**
     * 原始名称
     */
    public final String originalName;

    /**
     * 原始参数
     */
    public final String originalArguments;


    /**
     * Creates a new MethodInfo with the given properties.
     */
    public CustomizeMethodInfo(int obfuscatedFirstLineNumber,
                               int obfuscatedLastLineNumber,
                               String originalClassName,
                               int originalFirstLineNumber,
                               int originalLastLineNumber,
                               String originalType,
                               String originalName,
                               String originalArguments) {
        this.obfuscatedFirstLineNumber = obfuscatedFirstLineNumber;
        this.obfuscatedLastLineNumber = obfuscatedLastLineNumber;
        this.originalType = originalType;
        this.originalArguments = originalArguments;
        this.originalClassName = originalClassName;
        this.originalName = originalName;
        this.originalFirstLineNumber = originalFirstLineNumber;
        this.originalLastLineNumber = originalLastLineNumber;
    }


    /**
     * Returns whether the given properties match the properties of this
     * method. The given properties may be null wildcards.
     * @param obfuscatedLineNumber 已混淆行
     * @param originalType         原始类型
     * @param originalArguments    原始参数
     * @return true for matched.
     */
    public boolean matches(int obfuscatedLineNumber,
                           String originalType,
                           String originalArguments) {
        return
                // We're allowing unknown values, represented as 0.
                (obfuscatedLineNumber == 0 ||
                        obfuscatedLastLineNumber == 0 ||
                        (obfuscatedFirstLineNumber <= obfuscatedLineNumber &&
                                obfuscatedLineNumber <= obfuscatedLastLineNumber)) &&
                        (originalType == null || originalType.equals(this.originalType)) &&
                        (originalArguments == null || originalArguments.equals(this.originalArguments));
    }
}
