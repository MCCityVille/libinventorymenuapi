package de.mccityville.invmenu.internal.listener;

import de.mccityville.invmenu.MenuManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    private final MenuManager menuManager;

    public InventoryListener(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity whoClicked = event.getWhoClicked();
        if (!(whoClicked instanceof Player))
            return;
        boolean result = menuManager.dispatchClick((Player) whoClicked, event.getClickedInventory(), event.getSlot(), event.getClick());
        if (result) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity whoClosed = event.getPlayer();
        if (!(whoClosed instanceof Player))
            return;
        menuManager.dispatchClose((Player) whoClosed, event.getInventory());
    }
}
