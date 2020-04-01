package net.lapismc.arrowping;

import net.lapismc.lapiscore.compatibility.XSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ArrowPingListener implements Listener {

    public ArrowPingListener(ArrowPing plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void arrowHitEvent(EntityDamageByEntityEvent e){
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!(e.getDamager() instanceof Arrow)) {
            return;
        }
        if (!(((Arrow) e.getDamager()).getShooter() instanceof Player)) {
            return;
        }
        Player p = (Player) ((Arrow) e.getDamager()).getShooter();
        if(p == null)
            return;
        p.playSound(p.getLocation(), XSound.ENTITY_ARROW_HIT_PLAYER.parseSound(), 1f, 1f);
    }

}
