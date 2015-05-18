package br.com.wordcha.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuestionBase {

	final static Set<Question> questions = new LinkedHashSet<Question>();
	static {
		Theme fruta = new Theme("FRUTAS");
		questions.add(create(fruta, "Frutas que comecam com a letra A?", "AMEIXA", "AMORA", "ABACAXI", "ANANAS", "ACEROLA", "ACAI", "AMENDOIM"));
		questions.add(create(fruta, "Frutas que comecam com a letra C?", "CACAU", "CAJA", "CAJU", "CARAMBOLA", "CASTANHA", "CUPUACU", "CAQUI", "COCO"));
		questions.add(create(fruta, "Frutas que comecam com a letra G?", "GOIABA", "GRAVIOLA", "GROSELHA", "GUARANA"));

		Theme pais = new Theme("PAISES");
		questions.add(create(pais, "Paises da America latina?", "ARGENTINA", "BRASIL", "URUAGUAI", "PARAGUAI", "BOLIVIA", "CHILE", "PERU", "VENEZUELA"));
		questions.add(create(pais, "Paises da Europa?", "PORTUGAL", "INGLATERRA", "RUSSIA", "FRANÇA", "SUIÇA", "HOLANDA", "SUECIA", "BULGARIA", "ROMENIA", "ALEMANHA", "ESPANHA", "ITALIA", "GRECIA"));
		questions.add(create(pais, "Paises da Asia?", "JAPAO", "CHINA", "INDIA", "COREIA DO SUL", "COREIA DO NORTE", "TAILANDIA", "IRÃ", "IRAQUE", "FILIPINAS", "ISRAEL", "EMIRADOS ARABES", "CINGAPURA", "MALASIA", "VIETNÃ"));

	}
	private final static Random random = new Random();

	public static Question chooseOneButNot(Long[] ids) {
		System.err.println("nao pode achar esses: " + Arrays.asList(ids));
		if (ids.length >= questions.size()) {
			return null;
		}
		long id = random.nextInt(questions.size()) + 1;
		while (ids.length < questions.size() && Arrays.asList(ids).contains(id)) {
			id = random.nextInt(questions.size()) + 1;
		}
		for (Question q : questions) {
			if (q.getId() == id) {
				return q;
			}
		}
		return null;

	}

	static Question create(Theme theme, String value, String... answers) {
		Set<Answer> set = new HashSet<Answer>();
		Question q = new Question(theme, value, set);

		for (String string : answers) {
			q.getAnswers().add(new Answer(string, q));
		}

		System.err.println("created: " + q);
		return q;
	}

	public static void main(String[] args) {
		List<Long> used = new ArrayList<Long>();

		Question q = QuestionBase.chooseOneButNot(used.toArray(new Long[0]));
		System.err.println("achei:" + q);
		System.err.println("\n\n");
		for (int i = 0; i < 25; i++) {
			used.add(q.getId());
			q = QuestionBase.chooseOneButNot(used.toArray(new Long[0]));
			if(q==null){
				q = new Question();
				q.setId(0);
				used.clear();
				System.err.println("\n");
				continue;
			}
			System.err.println("achei:" + q);
		}

	}
}
