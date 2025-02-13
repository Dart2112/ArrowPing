package net.lapismc.arrowping;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;

public class ArrowPingListener implements Listener {

    private final ArrowPing plugin;
    //Stores if the console has been warned about an invalid sound name being used
    private boolean hasBeenWarned = false;

    public ArrowPingListener(ArrowPing plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void arrowHitEvent(EntityDamageByEntityEvent e) {
        //Check if the hit was from an arrow
        if (!(e.getDamager() instanceof Arrow)) {
            //Ignore if it's not an arrow
            return;
        }
        //Get the shooter
        ProjectileSource shooter = ((Arrow) e.getDamager()).getShooter();
        //Get the enabled shooters
        boolean isPlayerShooterEnabled = plugin.getConfig().getBoolean("Shooters.Players");
        boolean isSkeletonShooterEnabled = plugin.getConfig().getBoolean("Shooters.Skeletons");
        boolean isDispenserShooterEnabled = plugin.getConfig().getBoolean("Shooters.Dispensers");
        //Return if the shooter isn't enabled
        if (shooter instanceof Player && !isPlayerShooterEnabled)
            return;
        if (shooter instanceof Skeleton && !isSkeletonShooterEnabled)
            return;
        if (shooter instanceof BlockProjectileSource && !isDispenserShooterEnabled)
            return;

        //Get the entity that was hit
        Entity target = e.getEntity();
        //Get the enabled targets
        boolean isPlayerTargetEnabled = plugin.getConfig().getBoolean("Targets.Players");
        boolean isHostileMobTargetEnabled = plugin.getConfig().getBoolean("Targets.HostileMobs");
        boolean isPassiveMobsTargetEnabled = plugin.getConfig().getBoolean("Targets.PassiveMobs");
        //Return if the target isn't enabled
        if (target instanceof Player && !isPlayerTargetEnabled)
            return;
        if (target instanceof Monster && !isHostileMobTargetEnabled)
            return;
        if (target instanceof Creature && !isPassiveMobsTargetEnabled)
            return;

        //Check if PlayAloud is disabled while the target isn't a player
        boolean shouldPlayAloud = plugin.getConfig().getBoolean("PlayAloud");
        //If the shooter isn't a player, and play aloud is disabled, then we just don't play the sound
        if (!shouldPlayAloud && !(shooter instanceof Player))
            return;

        //We now know that the sound should be played. So let's get it.
        String soundNamespacedKey = plugin.getConfig().getString("PingSound", "entity.arrow.hit_player");
        Sound pingSound = Registry.SOUNDS.get(NamespacedKey.minecraft(soundNamespacedKey));
        if (pingSound == null) {
            if (!hasBeenWarned) {
                plugin.getLogger().warning("PingSound in config is not a valid sound, please check the config");
                hasBeenWarned = true;
            }
            return;
        }
        //Play the sound
        if (shouldPlayAloud) {
            Location soundLocation = null;
            //We need to determine the location of the shooter, this is easy if it's an entity, harder if it's a dispenser
            if (shooter instanceof Entity) {
                soundLocation = ((Entity) shooter).getLocation();
            } else if (shooter instanceof BlockProjectileSource) {
                soundLocation = ((BlockProjectileSource) shooter).getBlock().getLocation();
            }
            //Should never happen but rather be safe than sorry
            if (soundLocation == null)
                return;
            //Location determined, play the sound
            soundLocation.getWorld().playSound(soundLocation, pingSound, 1f, 1f);
        } else {
            //This should have to be a player by here
            Player shootingPlayer = (Player) shooter;
            //Play the sound from the player
            shootingPlayer.playSound(shootingPlayer, pingSound, 1f, 1f);
        }
    }

}
