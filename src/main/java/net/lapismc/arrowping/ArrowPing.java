package net.lapismc.arrowping;

import net.lapismc.lapiscore.LapisCorePlugin;

public final class ArrowPing extends LapisCorePlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new ArrowPingListener(this);
        super.onEnable();
    }

}
