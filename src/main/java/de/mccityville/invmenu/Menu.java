package de.mccityville.invmenu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class Menu {

    @Getter private final Inventory inventory;
    @Getter private final MenuManager menuManager;
    private MenuEntry[] menuEntries;
    @Getter @Setter private MenuListener listener;

    Menu(Inventory inventory, MenuManager menuManager) {
        this.inventory = inventory;
        this.menuManager = menuManager;
        initializeArray();
    }

    public InventoryView open(Player player) {
        InventoryView inventoryView = player.openInventory(inventory);
        if (listener != null)
            listener.onMenuOpen(this, player, inventoryView);
        return inventoryView;
    }

    public void clear() {
        initializeArray();
        inventory.clear();
    }

    public void destroy() {
        menuManager.destroy(inventory);
    }

    public MenuEntry getMenuEntry(int index) {
        return getMenuEntry(index, true);
    }

    public MenuEntry getMenuEntry(int index, boolean initializeEntryIfAbsent) {
        check(index);
        MenuEntry menuEntry = menuEntries[index];
        if (menuEntry == null && initializeEntryIfAbsent) {
            menuEntry = new MenuEntry(inventory, index);
            menuEntries[index] = menuEntry;
        }
        return menuEntry;
    }

    private void check(int index) {
        if (index < 0 || index >= menuEntries.length)
            throw new IllegalArgumentException("index must between 0 (inclusive) and " + menuEntries.length + " (exclusive)");
    }

    private void initializeArray() {
        menuEntries = new MenuEntry[inventory.getSize()];
    }

    public interface MenuListener {

        void onMenuOpen(Menu menu, Player player, InventoryView inventoryView);

        void onMenuClose(Menu menu, Player player);
    }

    public static class MenuListenerAdapter implements MenuListener {

        @Override
        public void onMenuOpen(Menu menu, Player player, InventoryView inventoryView) {
        }

        @Override
        public void onMenuClose(Menu menu, Player player) {
        }
    }
}
