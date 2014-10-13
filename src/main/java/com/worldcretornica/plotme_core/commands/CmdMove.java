package com.worldcretornica.plotme_core.commands;

import com.worldcretornica.plotme_core.PlotMe_Core;
import com.worldcretornica.plotme_core.api.IPlayer;
import com.worldcretornica.plotme_core.api.IWorld;
import com.worldcretornica.plotme_core.api.event.InternalPlotMoveEvent;

public class CmdMove extends PlotCommand {

    public CmdMove(PlotMe_Core instance) {
        super(instance);
    }

    public boolean exec(IPlayer p, String[] args) {
        if (plugin.cPerms(p, "PlotMe.admin.move")) {
            if (!plugin.getPlotMeCoreManager().isPlotWorld(p)) {
                p.sendMessage(RED + C("MsgNotPlotWorld"));
            } else if (args.length < 3 || args[1].isEmpty() || args[2].isEmpty()) {
                p.sendMessage(C("WordUsage") + ": " + RED + "/plotme " + C("CommandMove") + " <" + C("WordIdFrom") + "> <" + C("WordIdTo") + "> "
                                      + RESET + C("WordExample") + ": " + RED + "/plotme " + C("CommandMove") + " 0;1 2;-1");
            } else {
                String plot1 = args[1];
                String plot2 = args[2];
                IWorld w = p.getWorld();

                if (!plugin.getPlotMeCoreManager().isValidId(w, plot1) || !plugin.getPlotMeCoreManager().isValidId(w, plot2)) {
                    p.sendMessage(C("WordUsage") + ": " + RED + "/plotme " + C("CommandMove") + " <" + C("WordIdFrom") + "> <" + C("WordIdTo") + "> "
                                          + RESET + C("WordExample") + ": " + RED + "/plotme " + C("CommandMove") + " 0;1 2;-1");
                    return true;
                } else {
                    InternalPlotMoveEvent event = sob.getEventFactory().callPlotMoveEvent(plugin, w, w, plot1, plot2, p);

                    if (!event.isCancelled()) {
                        if (plugin.getPlotMeCoreManager().movePlot(p.getWorld(), plot1, plot2)) {
                            p.sendMessage(C("MsgPlotMovedSuccess"));

                            plugin.getLogger().info(LOG + p.getName() + " " + C("MsgExchangedPlot") + " " + plot1 + " " + C("MsgAndPlot") + " " + plot2);
                        } else {
                            p.sendMessage(RED + C("ErrMovingPlot"));
                        }
                    }
                }
            }
        } else {
            p.sendMessage(RED + C("MsgPermissionDenied"));
            return false;
        }
        return true;
    }
}
