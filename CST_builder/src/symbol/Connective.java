package symbol;

public class Connective extends Symbol {
	
	protected Connective(String value) {
		super();
		content = value;
		switch (value) {
		case "\\and":
			display = "∧ ";	
			break;
		case "\\or":
			display = "∨";
			break;
		case "\\not":
			display = "¬ ";
			break;
		case "\\imply":
			display = "→ ";
			break;
		case "\\eq":
			display = "↔ ";
		}
	}

}
