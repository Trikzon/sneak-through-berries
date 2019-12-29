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
 * File: ConfigObject.java
 * Date: 2019-12-29
 * Revision:
 * Author: Trikzon
 * ============================================================================= */
package io.github.trikzon.sneakthroughberries.fabric;

public class ConfigObject {

    private String _comment = "True if object is a requirement to avoid damage";
    public boolean wearingBoots;
    public boolean wearingLeggings;
    public boolean wearingAllArmor;
    public boolean wearingArmorAllowsWalking;
    public boolean sneaking;
    public boolean isPlayer;
    public boolean isNotPlayer;

    public ConfigObject(boolean wearingBoots, boolean wearingLeggings, boolean wearingAllArmor, boolean wearingArmorAllowsWalking, boolean sneaking, boolean isPlayer, boolean isNotPlayer) {
        this.wearingBoots = wearingBoots;
        this.wearingLeggings = wearingLeggings;
        this.wearingAllArmor = wearingAllArmor;
        this.wearingArmorAllowsWalking = wearingArmorAllowsWalking;
        this.sneaking = sneaking;
        this.isPlayer = isPlayer;
        this.isNotPlayer = isNotPlayer;
    }
}
