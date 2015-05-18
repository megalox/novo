package br.com.wordcha.core;

public class Answer {

	private long id;
	private String value;
	private Question question;

	public Answer(String value, Question question) {
		this.value = value;
		this.question = question;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
