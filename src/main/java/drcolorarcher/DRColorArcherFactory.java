package drcolorarcher;

import com.danieli1818.drminigames.common.arenalogics.TeamsArenaLogic;
import com.danieli1818.drminigames.common.arenalogics.TeamsArenaLogicFactory;
import com.danieli1818.drminigames.resources.api.Arena;
import com.danieli1818.drminigames.resources.api.ArenaLogic;

public class DRColorArcherFactory implements TeamsArenaLogicFactory {
	
	public DRColorArcherFactory() {}

	public TeamsArenaLogic create(Arena arena) {
		System.out.println("Yay! Created a drcolorarcher without args using the factory!");
		return new DRColorArcher(arena);
	}

	public ArenaLogic create(Arena arena, String[] args) {
		System.out.println("Yay! Created a drcolorarcher with args using the factory!");
		return new DRColorArcher(arena, args);
	}

}
