package de.mccityville.invmenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {

    private final Map<Inventory, Menu> menus = new HashMap<>();

    public Menu createMenu(int size, String title) {
        Inventory inventory = Bukkit.createInventory(null, size, title);
        Menu menu = new Menu(inventory, this);
        menus.put(inventory, menu);
        return menu;
    }

    public boolean dispatchClick(Player player, Inventory inventory, int slot, ClickType clickType) {
        Menu menu = menus.get(inventory);
        if (menu == null)
            return false;
        MenuEntry menuEntry = menu.getMenuEntry(slot, false);
        if (menuEntry == null)
            return true;
        MenuEntry.ClickListener clickListener = menuEntry.getClickListener();
        if (clickListener == null)
            return true;
        clickListener.onClick(menu, menuEntry, player, clickType);
        return true;
    }

    public void dispatchClose(Player player, Inventory inventory) {
        Menu menu = menus.get(inventory);
        if (menu == null)
            return;
        Menu.MenuListener menuListener = menu.getListener();
        if (menuListener != null)
            menuListener.onMenuClose(menu, player);
    }

    void destroy(Inventory inventory) {
        menus.remove(inventory);
    }

    public static MenuManager initialize(Plugin plugin) {
        MenuManager menuManager = Bukkit.getServicesManager().load(MenuManager.class);
        if (menuManager == null) {
            menuManager = new MenuManager();
            Bukkit.getPluginManager().registerEvents(new de.mccityville.invmenu.internal.listener.InventoryListener(menuManager), plugin);
        }
        return menuManager;
    }
}
