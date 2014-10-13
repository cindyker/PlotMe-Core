package com.worldcretornica.plotme_core.commands;

import com.worldcretornica.plotme_core.PlotMe_Core;
import com.worldcretornica.plotme_core.api.ILocation;
import com.worldcretornica.plotme_core.api.IPlayer;

public class CmdID extends PlotCommand {

    public CmdID(PlotMe_Core instance) {
        super(instance);
    }

    public boolean exec(IPlayer p) {
        if (plugin.cPerms(p, "PlotMe.admin.id")) {
            if (plugin.getPlotMeCoreManager().isPlotWorld(p)) {
                String plotid = plugin.getPlotMeCoreManager().getPlotId(p.getLocation());

                if (plotid.isEmpty()) {
                    p.sendMessage(RED + C("MsgNoPlotFound"));
                } else {
                    p.sendMessage(AQUA + C("WordPlot") + " ID: " + RESET + plotid);

                    ILocation bottom = plugin.getPlotMeCoreManager().getPlotBottomLoc(p.getWorld(), plotid);
                    ILocation top = plugin.getPlotMeCoreManager().getPlotTopLoc(p.getWorld(), plotid);

                    p.sendMessage(AQUA + C("WordBottom") + ": " + RESET + bottom.getBlockX() + BLUE + "," + RESET + bottom.getBlockZ());
                    p.sendMessage(AQUA + C("WordTop") + ": " + RESET + top.getBlockX() + BLUE + "," + RESET + top.getBlockZ());
                }
            } else {
                p.sendMessage(RED + C("MsgNotPlotWorld"));
            }
        } else {
            p.sendMessage(RED + C("MsgPermissionDenied"));
            return false;
        }
        return true;
    }
}
