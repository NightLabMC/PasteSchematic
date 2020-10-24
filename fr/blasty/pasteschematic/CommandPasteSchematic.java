package fr.blasty.pasteschematic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

public class CommandPasteSchematic implements CommandExecutor {
	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	
    	String schemName = args[0];
    	World world = Bukkit.getServer().getWorld(args[1]);
    	int x = Integer.parseInt(args[2]);
    	int y = Integer.parseInt(args[3]);
    	int z = Integer.parseInt(args[4]);
    	
    	if(sender.isOp()){
    		
    		File schemFile = new File(PasteSchematic.getInstance().getDataFolder().getAbsolutePath() + "/" + schemName + ".schem");
    	    if(!schemFile.exists()){
            	System.out.println("\n[SchematicPaste] Schematic not found!");
    	    	return true;
    	    }
    	    
    		Clipboard clipboard = null;
    		
    		ClipboardFormat format = ClipboardFormats.findByFile(schemFile);
    		try (ClipboardReader reader = format.getReader(new FileInputStream(schemFile))) {
    		   clipboard = reader.read();
    		} catch (IOException e) {
				e.printStackTrace();
			}	
    		
    		if(clipboard == null){
            	System.out.println("\n[SchematicPaste] clipboard is null!");
    			return true;
    		}
    		
    		BukkitWorld BWf = new BukkitWorld(world);
    		
    		try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BWf, -1)) {
    		    Operation operation = new ClipboardHolder(clipboard)
    		            .createPaste(editSession)
    		            .to(BlockVector3.at(x, y, z))
    		            .ignoreAirBlocks(false)
    		            .build();
    		    Operations.complete(operation);
    		} catch (WorldEditException e) {
				e.printStackTrace();
			}
    		
            return true;
    	}
    	return false;
    }
}
