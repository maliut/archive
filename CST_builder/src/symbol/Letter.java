package symbol;

public class Letter extends Symbol {
	
	protected Letter(String value) {
		super();
		content = value;
		display = value.replaceAll("_\\{|\\}", "");
	}

}
