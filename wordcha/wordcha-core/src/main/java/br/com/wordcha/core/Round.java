package br.com.wordcha.core;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Round implements Runnable {

	private long id;
	private Match match;
	private Set<Cmd> commands = new LinkedHashSet<Cmd>();
	private Question question;
	private int countDown = 60;
	private boolean finished;

	static AtomicInteger counter = new AtomicInteger();

	public Round(Match match) {
		id = counter.addAndGet(1);
		this.match = match;
		Set<Long> col = new LinkedHashSet<Long>();
		match.getRounds().stream().filter((Round r) -> r.getQuestion() != null).forEach(r -> col.add(r.getQuestion().getId()));
		question = QuestionBase.chooseOneButNot(col.toArray(new Long[0]));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Set<Cmd> getCommands() {
		return commands;
	}

	public void setCommands(Set<Cmd> commands) {
		this.commands = commands;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getCountDown() {
		return countDown;
	}

	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}

	public static AtomicInteger getCounter() {
		return counter;
	}

	public static void setCounter(AtomicInteger counter) {
		Round.counter = counter;
	}

	public synchronized void addCmd(Cmd cmd) throws Exception {
		validate(cmd);
		commands.add(cmd);

	}

	private void check() {
		// TODO Auto-generated method stub
		if (commands.size() == match.getJoins().size()) {
			finished = true;
			match.begin();
		}
	}

	private void validate(Cmd cmd) throws Exception {
		if (countDown == 0) {
			throw new Exception("Tempo esgotado");
		}
		if (match.isFinished()) {
			throw new Exception(match.getName() + " finalizado");
		}
		if (commands.size() == match.getJoins().size()) {
			throw new Exception("Total de respostas ja enviadas");
		}
		if (commands.stream().anyMatch((Cmd c) -> c.equals(cmd))) {
			throw new Exception("Resposta repetida");
		}
		if (question.getAnswers().stream().anyMatch((Answer a) -> a.getValue().equalsIgnoreCase(cmd.getValue()))) {
			throw new Exception("Resposta Invalida");
		}

	}

	@Override
	public void run() {

		while (countDown > 0 || !finished) {
			try {
				check();
				countDown--;
				Thread.sleep(1000);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}
}
