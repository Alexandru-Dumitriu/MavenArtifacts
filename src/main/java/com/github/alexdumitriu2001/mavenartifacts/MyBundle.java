// Copyright - https://github.com/JetBrains/intellij-sdk-code-samples/blob/main/comparing_string_references_inspection/src/main/java/org/intellij/sdk/codeInspection/InspectionBundle.java
package com.github.alexdumitriu2001.mavenartifacts;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public final class MyBundle extends DynamicBundle {

    private static final MyBundle ourInstance = new MyBundle();

    @NonNls
    public static final String BUNDLE = "messages.MyBundle";

    private MyBundle() {
        super(BUNDLE);
    }

    public static @Nls String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key,
                                      Object @NotNull ... params) {
        return ourInstance.getMessage(key, params);
    }

}