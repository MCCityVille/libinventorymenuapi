package de.mccityville.invmenu;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public class MenuEntry {

    private final Inventory inventory;
    private final int slotIndex;
    private Map<String, Object> metadata;
    private ClickListener clickListener;

    public Map<String, Object> getMetadata() {
        if (metadata == null)
            metadata = new HashMap<>(0);
        return metadata;
    }

    public void setDisplay(ItemStack itemStack) {
        inventory.setItem(slotIndex, itemStack);
    }

    public ItemStack getDisplay() {
        return inventory.getItem(slotIndex);
    }

    @FunctionalInterface
    public interface ClickListener {

        void onClick(Menu menu, MenuEntry menuEntry, Player player, ClickType clickType);
    }

    public abstract static class ClickListenerAdapter implements ClickListener {

        @Override
        public final void onClick(Menu menu, MenuEntry menuEntry, Player player, ClickType clickType) {
            switch (clickType) {
                case LEFT:
                    onLeftClick(menu, menuEntry, player, false);
                    break;
                case SHIFT_LEFT:
                    onLeftClick(menu, menuEntry, player, true);
                    break;
                case RIGHT:
                    onRightClick(menu, menuEntry, player, false);
                    break;
                case SHIFT_RIGHT:
                    onRightClick(menu, menuEntry, player, true);
                    break;
            }
            onClick0(menu, menuEntry, player, clickType);
        }

        protected void onLeftClick(Menu menu, MenuEntry menuEntry, Player player, boolean shiftHold) {
        }

        protected void onRightClick(Menu menu, MenuEntry menuEntry, Player player, boolean shiftHold) {
        }

        protected void onClick0(Menu menu, MenuEntry menuEntry, Player player, ClickType clickType) {
        }
    }
}
