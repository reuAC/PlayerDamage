package com.playerdamage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class PlayerDamageListener implements Listener {
    public static boolean damageEnable;
    public static boolean attackEnable;

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (damageEnable){
            event.setDamage(Double.parseDouble(PlayerDamage.main.getConfig().getString("Damage")));
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if (attackEnable){
            LivingEntity damagedEntity = (LivingEntity) event.getEntity();
            delaySetNoDamageTicks(Integer.parseInt(PlayerDamage.main.getConfig().getString("Attack")),damagedEntity);
        }
    }

    private void delaySetNoDamageTicks(int ticks,LivingEntity attack) {
        new BukkitRunnable() {
            @Override
            public void run() {
                attack.setNoDamageTicks(ticks);
            }
        }.runTaskLater(PlayerDamage.main, 1);
    }
}
