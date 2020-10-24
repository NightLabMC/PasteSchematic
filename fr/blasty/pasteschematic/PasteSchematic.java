package fr.blasty.pasteschematic;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class PasteSchematic extends JavaPlugin{

    public static PasteSchematic instance;
    
    public static PasteSchematic getInstance() {
    	return instance;
    }
    
    public PasteSchematic() {
    	instance = this;
    }
    
    public void onEnable() {
    	System.out.println("\n[SchematicPaste] Initializing Plugin");
    	
        if (getServer().getPluginManager().getPlugin("WorldEdit") == null) {
        	System.out.println("\n[SchematicPaste] WorldEdit not found!");
            getServer().getPluginManager().disablePlugin(this);
        }
        
        File schemFile = new File(PasteSchematic.getInstance().getDataFolder().getAbsolutePath());
	    if(!schemFile.exists()){
	    	schemFile.mkdirs();
	        try {
	        	schemFile.createNewFile();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	    }
	    
        getCommand("SchemPaste").setExecutor((CommandExecutor)new CommandPasteSchematic());
	}
    
}
