/* =============================================================================
 * Copyright 2019 Trikzon
 *
 * Sneak-Through-Berries is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * File: Config.java
 * Date: 2019-12-29
 * Revision:
 * Author: Trikzon
 * ============================================================================= */
package io.github.trikzon.sneakthroughberries.forge;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

public class Config {

    public static final String CATEGORY_DAMAGE_REQUIREMENT = "damage_requirement";

    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue WEARING_BOOTS;
    public static ForgeConfigSpec.BooleanValue WEARING_LEGGINGS;
    public static ForgeConfigSpec.BooleanValue WEARING_ALL_ARMOR;
    public static ForgeConfigSpec.BooleanValue WEARING_ARMOR_ALLOWS_WALKING;
    public static ForgeConfigSpec.BooleanValue SNEAKING;
    public static ForgeConfigSpec.BooleanValue IS_PLAYER;
    public static ForgeConfigSpec.BooleanValue IS_NOT_PLAYER;

    static {
        COMMON_BUILDER.comment("Damage Requirement Settings").push(CATEGORY_DAMAGE_REQUIREMENT);
        setupDamageRequirementConfig();
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void setupDamageRequirementConfig() {
        WEARING_BOOTS = COMMON_BUILDER.comment("Require Player Wearing Boots To Avoid Damage By Sweet Berry Bushes")
                .define("wearingBoots", true);
        WEARING_LEGGINGS = COMMON_BUILDER.comment("Require Player Wearing Leggings To Avoid Damage By Sweet Berry Bushes")
                .define("wearingLeggings", true);
        WEARING_ALL_ARMOR = COMMON_BUILDER.comment("Require Player Wearing All Armor Slots To Avoid Damage By Sweet Berry Bushes")
                .define("wearingAllArmor", false);
        WEARING_ARMOR_ALLOWS_WALKING = COMMON_BUILDER.comment("If True, Armor Is Not Required... But Any Armor That Would Of Been Required Now Is Required To Walk Through Sweet Berry Bushes Without Taking Damage")
                .define("wearingArmorAllowsWalking", true);
        SNEAKING = COMMON_BUILDER.comment("Require Entity To Sneak To Avoid Damage By Sweet Berry Bushes")
                .define("sneaking", true);
        IS_PLAYER = COMMON_BUILDER.comment("Require Entity To Be A PlayerEntity To Avoid Damage By Sweet Berry Bushes")
                .define("isPlayer", false);
        IS_NOT_PLAYER = COMMON_BUILDER.comment("Require Entity To Not Be A PlayerEntity To Avoid Damage By Sweet Berry Bushes")
                .define("isNotPlayer", false);
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.ConfigReloading event) {

    }
}
