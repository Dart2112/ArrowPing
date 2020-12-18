package net.lapismc.arrowping;

import net.lapismc.lapiscore.compatibility.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ArrowPingListener implements Listener {

    public ArrowPingListener(ArrowPing plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void arrowHitEvent(EntityDamageByEntityEvent e) {
        //Check if the hit was from an arrow
        if (!(e.getDamager() instanceof Arrow)) {
            //Ignore if its not an arrow
            return;
        }
        //Check if the thing that shot the arrow was a living entity e.g. not a dispenser
        if (!(((Arrow) e.getDamager()).getShooter() instanceof LivingEntity)) {
            return;
        }
        //Get the location of the entity that shot the arrow
        Location loc = ((LivingEntity) ((Arrow) e.getDamager()).getShooter()).getLocation();
        //Play a sound at the entity to signify that they hit another entity
        loc.getWorld().playSound(loc, XSound.ENTITY_ARROW_HIT_PLAYER.parseSound(), 1f, 1f);
    }

}
