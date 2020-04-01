package net.lapismc.arrowping;

import net.lapismc.lapiscore.LapisCorePlugin;

public final class ArrowPing extends LapisCorePlugin {

    @Override
    public void onEnable() {
        new ArrowPingListener(this);
        getLogger().info(getName() + " v." + getDescription().getVersion() + " has been enabled!");
    }

}
