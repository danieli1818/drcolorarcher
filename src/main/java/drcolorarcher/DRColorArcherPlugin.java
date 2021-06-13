package drcolorarcher;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.danieli1818.drminigames.resources.api.ArenaLogicFactory;
import com.danieli1818.drminigames.resources.api.DRMinigamePlugin;

public class DRColorArcherPlugin extends JavaPlugin implements DRMinigamePlugin {

	public ArenaLogicFactory getArenaLogicFactory() {
		return new DRColorArcherFactory();
	}

	public void registerSerializableClasses() {
		ConfigurationSerialization.registerClass(DRColorArcher.class);
		ConfigurationSerialization.registerClass(BlockPointsInformation.class);
	}

	public void unregisterSerializableClasses() {
		ConfigurationSerialization.unregisterClass(DRColorArcher.class);
		ConfigurationSerialization.unregisterClass(BlockPointsInformation.class);
	}

	@Override
	public String getID() {
		return "DRColorArcher";
	}

}
