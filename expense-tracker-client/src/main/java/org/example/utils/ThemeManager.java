package org.example.utils;

import javafx.scene.Scene;
import java.util.Objects;

public final class ThemeManager {

    private static boolean isDark = true;

    private static final String LIGHT = "/style-light.css";
    private static final String DARK = "/style-dark.css";

    private ThemeManager() {}

    public static boolean isDark() {
        return isDark;
    }

    public static void setDark(boolean dark) {
        isDark = dark;
    }

    public static void apply(Scene scene) {
        if (scene == null) return;
        scene.getStylesheets().clear();
        scene.getStylesheets().add(
            Objects.requireNonNull(ThemeManager.class.getResource(isDark ? DARK : LIGHT)).toExternalForm()
        );
    }
}