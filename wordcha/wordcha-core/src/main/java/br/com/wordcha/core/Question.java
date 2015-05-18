package br.com.wordcha.core;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Question {

	private long id;
	private Theme theme;
	private String value;
	private Set<Answer> answers;

	final static AtomicLong counter = new AtomicLong();

	public Question() {

	}

	public Question(Theme theme, String value, Set<Answer> answers) {
		super();
		this.theme = theme;
		this.value = value;
		this.answers = answers;
		id = counter.addAndGet(1);

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

}
