package net.uku3lig.ukulib.config.impl;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.gui.screen.Screen;
import net.uku3lig.ukulib.config.option.CyclingOption;
import net.uku3lig.ukulib.config.option.InputOption;
import net.uku3lig.ukulib.config.option.WidgetCreator;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

import java.util.Locale;

/**
 * ukulib's config screen.
 */
@Slf4j
public class UkulibConfigScreen extends AbstractConfigScreen<UkulibConfig> {
    /**
     * Creates a config screen.
     *
     * @param parent The parent screen
     */
    public UkulibConfigScreen(Screen parent) {
        super("ukulib.config.title", parent, UkulibConfig.getManager());
    }

    @Override
    protected WidgetCreator[] getWidgets(UkulibConfig config) {
        return new WidgetCreator[]{
                CyclingOption.ofBoolean("ukulib.config.buttonInOptions", config.isButtonInOptions(), config::setButtonInOptions),
                CyclingOption.ofBoolean("ukulib.config.modMenuIntegration", config.isModMenuIntegration(), config::setModMenuIntegration),
                new InputOption("ukulib.config.headName", config.getHeadName(), config::setHeadName,
                        s -> s.toLowerCase(Locale.ROOT).matches("[a-z0-9_-]+"))
        };
    }
}
