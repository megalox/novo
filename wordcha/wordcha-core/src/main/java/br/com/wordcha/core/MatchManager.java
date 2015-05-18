package br.com.wordcha.core;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public class MatchManager implements Runnable {

	private Set<Match> spotLight = null;
	private static MatchManager matchManager = null;

	private MatchManager() {
		spotLight = new LinkedHashSet<Match>();
	}

	public static MatchManager startAndGetOrJustGet() {
		if (matchManager == null) {
			synchronized ("match_manager") {
				if (matchManager == null) {
					matchManager = new MatchManager();
					new Thread(matchManager).start();
				}
			}
		}
		return matchManager;
	}

	public Match chooseFromfilteredSpotLights(long matchId) {
		Stream<Match> stream = filteredSpotLights().stream().filter((Match m) -> m.getId() == matchId);
		if (stream.count() == 0)
			return null;
		
		return filteredSpotLights().stream().filter((Match m) -> m.getId() == matchId).findFirst().get();

	}

	public Set<Match> filteredSpotLights() {
		Set<Match> sets = new LinkedHashSet<Match>();
		spotLight.stream().filter((Match m) -> m.isPayable()).limit(1).forEach((Match m) -> sets.add(m));
		spotLight.stream().filter((Match m) -> m.isStarted() && !m.isPayable()).limit(3).forEach((Match m) -> sets.add(m));
		spotLight.stream().filter((Match m) -> !m.isStarted() && !m.isPayable()).limit(3).forEach((Match m) -> sets.add(m));
		return sets;
	}

	private void manageSpotLight() {

		spotLight.removeIf((Match m) -> m.isOver());

		if (spotLight.size() < 10) {
			spotLight.add(createMatch());
		}
		spotLight.stream().filter((Match m) -> !m.isOver() && !m.isStarted()).forEach(m -> m.begin());

	}

	static Random random = new Random();

	public static Match createMatch() {

		Match match = new Match("Match", random.nextBoolean(), 10, 1, LocalDateTime.now().plusMinutes(1));
		return match;
	}

	public void manage() {

		manageSpotLight();
		printSpotLight();

	}

	private void printSpotLight() {

		System.err.println("\n----------TUDO----");
		spotLight.forEach((Match m) -> System.err.println(m));

		System.err.println("\n----------filter----");
		filteredSpotLights().forEach((Match m) -> System.err.println(m));
		System.err.println("\n--------------------");

	}

	@Override
	public void run() {

		try {
			while (true) {
				manage();
				Thread.sleep(5000);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			matchManager = null;
		}

	}

	public static void main(String[] args) throws InterruptedException {

		MatchManager manager = MatchManager.startAndGetOrJustGet();

		Thread.sleep(6000);
		Match match = manager.chooseFromfilteredSpotLights(1);
		System.err.println("achou:" + match);
	}

}
