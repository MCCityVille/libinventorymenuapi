package de.mccityville.invmenu.utils;

import de.mccityville.invmenu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class MenuUtils {

    private static final Menu.MenuListener DESTROY_LISTENER = new Menu.MenuListenerAdapter() {
        @Override
        public void onMenuClose(Menu menu, Player player) {
            menu.destroy();
        }
    };

    private MenuUtils() {
    }

    public static Menu.MenuListener destroyingListener() {
        return DESTROY_LISTENER;
    }

    public static Menu.MenuListener destroyingListener(Menu.MenuListener delegate) {
        return new Menu.MenuListener() {
            @Override
            public void onMenuOpen(Menu menu, Player player, InventoryView inventoryView) {
                DESTROY_LISTENER.onMenuOpen(menu, player, inventoryView);
                delegate.onMenuOpen(menu, player, inventoryView);
            }

            @Override
            public void onMenuClose(Menu menu, Player player) {
                DESTROY_LISTENER.onMenuClose(menu, player);
                delegate.onMenuClose(menu, player);
            }
        };
    }
}
