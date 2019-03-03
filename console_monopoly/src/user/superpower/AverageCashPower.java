package user.superpower;

import java.util.Collection;

import user.component.Player;

public class AverageCashPower extends Superpower {

	@Override
	public boolean execute() {
		Collection<Player> players = getAllPlayers();
		int avgCash = players.stream().mapToInt(p -> p.getCash()).sum() / players.size();
		players.forEach(p -> p.setCash(avgCash));
		return true;
	}
	
	private static AverageCashPower power;
	
	public static AverageCashPower getInstance() {
		if (power == null) power = new AverageCashPower();
		return power;
	}
	
	private AverageCashPower() {
		
	}

}
