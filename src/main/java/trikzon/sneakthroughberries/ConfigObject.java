package trikzon.sneakthroughberries;

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
