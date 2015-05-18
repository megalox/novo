package br.com.wordcha.core;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Match {

	private long id;
	private String name;
	private boolean payable;
	private int maxJoin;
	private int bet;
	private LocalDateTime start;
	private Set<Join> joins = new HashSet<Join>();
	private Set<Round> rounds = new HashSet<Round>();
	private boolean finished;
	private boolean started;

	static AtomicInteger counter = new AtomicInteger();

	public Match(String name, boolean payable, int maxJoin, int bet, LocalDateTime start) {
		super();
		this.id = counter.addAndGet(1);
		this.name = name + " " + id;
		this.payable = payable;
		this.maxJoin = maxJoin;
		this.bet = bet;
		this.start = start;

	}

	public boolean isOver() {

		return finished || (LocalDateTime.now().isAfter(start) && joins.isEmpty());
	}

	@Override
	public String toString() {
		return "Match [name=" + name + ", payable=" + payable + ", maxJoin=" + maxJoin + ", bet=" + bet + ", start=" + start + ", started=" + started + ", finished=" + finished + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPayable() {
		return payable;
	}

	public void setPayable(boolean payable) {
		this.payable = payable;
	}

	public int getMaxJoin() {
		return maxJoin;
	}

	public void setMaxJoin(int maxJoin) {
		this.maxJoin = maxJoin;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public Set<Join> getJoins() {
		return joins;
	}

	public void setJoins(Set<Join> joins) {
		this.joins = joins;
	}

	public Set<Round> getRounds() {
		return rounds;
	}

	public void setRounds(Set<Round> rounds) {
		this.rounds = rounds;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public void begin() {
		started = true;
		rounds.add(new Round(this));

	}

}
