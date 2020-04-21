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
        //Check if a player has been shot
        if (e.getEntity() instanceof Player) {
            //Ignore since hitting a player already makes a sound
            return;
        }
        //Check if the hit was from an arrow
        if (!(e.getDamager() instanceof Arrow)) {
            //Ignore if its not an arrow
            return;
        }
        //Check if the thing that shot the arrow was a player
        if (!(((Arrow) e.getDamager()).getShooter() instanceof Player)) {
            //TODO: maybe play a sound at the location of the shooter, e.g. if a skeleton hits someone you can hear a ding come from the skeleton
            return;
        }
        //Get the player who shot the arrow
        Player p = (Player) ((Arrow) e.getDamager()).getShooter();
        //Make sure the player is online, theoretically the player could leave after an arrow is shot and before it lands?
        //Maybe not, best to null check anyways
        if (p == null)
            return;
        //Play a sound to the player to signify that they hit an entity
        p.playSound(p.getLocation(), XSound.ENTITY_ARROW_HIT_PLAYER.parseSound(), 1f, 1f);
    }

}
