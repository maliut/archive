package kernel.util;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Range {
	
	private ArrayList<Integer> r;
	
	public Range(int min, int max) {
		for (int i = min; i < max; i++) {
			r.add(i);
		}
	}
	
	public void forEach(Consumer<? super Integer> action) {
		r.forEach(action);
	}

}
