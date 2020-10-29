package dev.alexzvn.holywater;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.util.Vector;

public class LiquidHandler implements Listener {
    
    protected FileConfiguration config;

    public LiquidHandler(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void handle(BlockFromToEvent event) {
        
        if (isAllowed(event.getBlock().getWorld())) {
            return;
        }

        if (shouldPrevent(event)) {
            event.setCancelled(true);
        }
    }

    protected boolean shouldPrevent(BlockFromToEvent event) {

        int distance = distance(event.getBlock(), event.getToBlock());

        if (event.getBlock().getType().name().equals("WATER")) {
            return shouldPreventLiquid(distance, "water");
        }

        return shouldPreventLiquid(distance, "lava");
    }

    protected boolean shouldPreventLiquid(int distance, String type) {
        int maxDistance = config.getInt("distance.".concat(type), -1);

        if (maxDistance != -1 && distance >= maxDistance) {
            return true;
        }

        return false;
    }

    protected boolean isAllowed(World world) {
        
        for (String worldName : config.getStringList("allowed_world")) {
            if (world.getName().equals(worldName)) {
                return true;
            }
        }

        return false;
    }

    protected int distance(Block a, Block b) {
        return (int) vectorFromBlock(a).distance(vectorFromBlock(b));
    }

    protected Vector vectorFromBlock(Block block) {
        return new Vector(
            block.getX(),
            block.getY(),
            block.getZ()
        );
    }
}
