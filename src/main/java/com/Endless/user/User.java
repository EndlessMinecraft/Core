package com.Endless.user;

import com.Endless.ELCore;
import org.bukkit.entity.Player;

public class User {
    private Player player;

    private UserRank rank;

    ELCore plugin = (ELCore)(ELCore.getPlugin(ELCore.class));

    public User(Player player){
        this.player = player;
        this.rank = ELCore.getInstance().getDatabaseManager().getRank(player);
    }

    public boolean  compareRank(UserRank playerRank){
        return (getRank().ordinal() >= playerRank.ordinal());
    }

    public Player getPlayer() {
        return this.player;
    }

    public UserRank getRank() {
        return this.rank;
    }

    public String getPrefix() {
        return getRank().getPrefix();
    }

    public boolean isStaff() {
        return getRank().isStaff();
    }

}
