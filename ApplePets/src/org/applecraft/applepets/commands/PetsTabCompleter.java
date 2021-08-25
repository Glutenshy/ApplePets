package org.applecraft.applepets.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class PetsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
    	  List<String> cmds = new ArrayList<String>();

    	  switch (args.length) {
    	  case 1:
    	    cmds.add("remove");
    	    if (sender.hasPermission("applepets.pet.reload")) cmds.add("reload");
    	    return StringUtil.copyPartialMatches(args[0], cmds, new ArrayList<String>());
    	  }
    	  return null;
    }
}

