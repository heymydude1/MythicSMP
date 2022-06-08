package me.heymydude.startupcommands.Util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {
    private ItemStack itemStack;
    Event.Result deny = BlockPlaceEvent.Result.DENY;

    public ItemCreator(Material material) {
        this(material, 0);
    }

    public ItemCreator(Material material, int data) {
        this(material, (byte) data);
    }

    public ItemCreator(Material material, byte data) {
        this(new ItemStack(material, 1, data));
    }

    public ItemCreator(ItemStack itemStack) {
        this(itemStack, 0);
    }

    public ItemCreator(ItemStack itemStack, int data) {
        this.itemStack = itemStack.clone();
        setData(data);
    }

    public ItemCreator setName(String name) {
        return setName(name, true);
    }

    public ItemCreator setName(String name, boolean color) {
        if (name != null) {
            ItemMeta meta = getItemStack().getItemMeta();
            if (meta != null) {
                meta.setDisplayName(color ? Msg.color(name) : name);
                getItemStack().setItemMeta(meta);
            }
        }
        return this;
    }

    public ItemCreator setType(Material type) {
        getItemStack().setType(type);
        return this;
    }

    public Material getType() {
        return getItemStack().getType();
    }

    public ItemCreator setAmount(int amount) {
        getItemStack().setAmount(amount);
        return this;
    }

    public int getAmount() {
        return getItemStack().getAmount();
    }

    public ItemCreator setData(int data) {
        return setData((byte) data);
    }

    public ItemCreator setData(byte data) {
        MaterialData materialData = itemStack.getData();
        materialData.setData(data);
        itemStack.setData(materialData);
        return this;
    }

    public ItemCreator setUnbreakable(boolean value) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(value);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setFlags(ItemFlag... flags) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator clearLores() {
        setLores(new String [] {});
        return this;
    }

    public ItemCreator setLores(String [] lores) {
        ItemMeta meta = getItemStack().getItemMeta();
        if (meta != null) {
            meta.setLore(new ArrayList<>());
            getItemStack().setItemMeta(meta);
            for (String lore : lores) {
                if (lore != null) {
                    addLore(lore);
                }
            }
        }
        return this;
    }

    public ItemCreator setLores(List<String> lores) {
        ItemMeta meta = getItemStack().getItemMeta();
        if (meta != null) {
            meta.setLore(new ArrayList<>());
            getItemStack().setItemMeta(meta);
            for (String lore : lores) {
                if (lore != null) {
                    addLore(lore);
                }
            }
        }
        return this;
    }

    public ItemCreator addLore(String lore) {
        if (lore == null) {
            return this;
        }
        ItemMeta meta = getItemStack().getItemMeta();
        if (meta != null) {
            List<String> lores = meta.getLore();
            if (lores == null) {
                lores = new ArrayList<>();
            }
            lores.add(Msg.color(lore));
            meta.setLore(lores);
            getItemStack().setItemMeta(meta);
        }
        return this;
    }

    public String [] getLoreArray() {
        List<String> loreList = getLores();
        String [] lores = new String [loreList.size()];
        for (int a = 0; a < loreList.size(); ++a) {
            lores[a] = loreList.get(a);
        }
        return lores;
    }

    public List<String> getLores() {
        return getItemStack().getItemMeta().getLore();
    }

    public ItemCreator addEnchantment(Enchantment enchantment) {
        return addEnchantment(enchantment, 1);
    }

    public ItemCreator addEnchantment(Enchantment enchantment, int level) {
        getItemStack().addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public ItemCreator setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public String getName() {
        return getItemStack().getItemMeta().getDisplayName();
    }
}


