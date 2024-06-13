package net.uku3lig.ukulib.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.nio.file.Path;
import java.util.Locale;

/**
 * Simple class for various utilities.
 */
public class Ukutils {
    /**
     * Creates a done button.
     *
     * @param width  The width of the screen
     * @param height The height of the screen
     * @param parent The parent screen
     * @return The generated button
     */
    public static ButtonWidget doneButton(int width, int height, Screen parent) {
        return ButtonWidget.builder(ScreenTexts.DONE, button -> MinecraftClient.getInstance().setScreen(parent))
                .dimensions(width / 2 - 100, height - 27, 200, 20)
                .build();
    }

    /**
     * Makes text coordinates based on the position of an icon.
     *
     * @param text         The text to be drawn
     * @param screenWidth  The width of the screen
     * @param textRenderer The text renderer
     * @param x            The x coordinate of the icon
     * @param y            The y coordinate of the icon
     * @param width        The width of the icon
     * @param height       The height of the icon
     * @return The tuple of coordinates
     */
    public static Vector2ic getTextCoords(Text text, int screenWidth, TextRenderer textRenderer, int x, int y, int width, int height) {
        Vector2i vector = new Vector2i(
                x + (width / 2) - (textRenderer.getWidth(text) / 2), // center
                y + height + 2 - textRenderer.fontHeight // center
        );

        int rx = x - ((screenWidth - width) / 2);
        if (Math.abs(rx) >= 2) {
            vector.set(
                    rx < 0 ? x + width + 2 /* left */ : x - 2 - textRenderer.getWidth(text) /* right */,
                    y + (height / 2) - (textRenderer.fontHeight / 2) // left/right
            );
        }

        return vector;
    }

    /**
     * Makes text coordinates based on the position of a standard 16x16 icon.
     *
     * @param text         The text to be drawn
     * @param screenWidth  The width of the screen
     * @param textRenderer The text renderer
     * @param x            The x coordinate of the icon
     * @param y            The y coordinate of the icon
     * @return The tuple of coordinates
     * @see Ukutils#getTextCoords(Text, int, TextRenderer, int, int, int, int)
     */
    public static Vector2ic getTextCoords(Text text, int screenWidth, TextRenderer textRenderer, int x, int y) {
        return getTextCoords(text, screenWidth, textRenderer, x, y, 16, 16);
    }

    /**
     * Retrieves the string text from an ordered text.
     *
     * @param text The ordered text
     * @return The value of the text
     */
    @SuppressWarnings("unused")
    public static String getText(OrderedText text) {
        StringBuilder builder = new StringBuilder();
        text.accept((index, style, codePoint) -> {
            builder.append(Character.toChars(codePoint));
            return true;
        });

        return builder.toString();
    }

    /**
     * Creates a config path for a file name.
     *
     * @param name The name of the file
     * @return The path to the file
     */
    public static Path getConfigPath(String name) {
        return FabricLoader.getInstance().getConfigDir().resolve(name);
    }

    /**
     * Displays a simple toast message.
     *
     * @param title The title of the message
     * @param body  The body of the message
     */
    public static void sendToast(Text title, @Nullable Text body) {
        ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
        SystemToast.show(toastManager, SystemToast.Type.NARRATOR_TOGGLE, title, body);
    }

    /**
     * Checks if a texture exists and is registered.
     *
     * @param texture The texture to check
     * @return Whether the texture exists
     */
    public static boolean textureExists(Identifier texture) {
        if (texture == null) {
            return false;
        } else {
            return MinecraftClient.getInstance().getTextureManager().getOrDefault(texture, null) != null;
        }
    }

    /**
     * Gets the texture of a player's head.
     *
     * @param username The username of the player
     * @return The texture of the player's head
     */
    @Nullable
    public static Identifier getHeadTex(String username) {
        if (Identifier.isPathValid(username)) {
            username = username.toLowerCase(Locale.ROOT);
            return Identifier.of("ukulib", "head_" + username);
        } else {
            return null;
        }
    }

    private Ukutils() {
    }
}
