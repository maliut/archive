package kernel.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class LoopList<E> {
	
	private List<E> list;
	private int currentIndex = 0;

	public LoopList() {
		list = new ArrayList<E>();
	}
	
	public LoopList(List<E> list) {
		this.list = list; 
	}
	
	public void add(E e) {
		list.add(e);
	}
	
	public boolean remove(E e) {
		int index = 0;
		while (index < list.size()) {
			if (list.get(index) == e) break;
			index++;
		}
		if (currentIndex > index) currentIndex--;
		return list.remove(e);
	}
	
	public E get(int index) {
		int size = list.size();
		int t_index = index % size;
		t_index += (t_index < 0) ? size : 0;
		return list.get(t_index);
	}
	
	public E getCurrent() {
		return list.get(currentIndex);
	}
	
	public E getNext() {
		return list.get(currentIndex + 1);
	}
	
	public E getNext(int r) {
		return get(currentIndex);
	}
	
	public void moveNext() {
		currentIndex += 1;
		currentIndex %= list.size();
	}
	
	public void movePrevious() {
		currentIndex -= 1;
		currentIndex %= list.size();
	}
	
	public void forEach(Consumer<? super E> action) {
		list.forEach(action);
	}
	
	public Stream<E> stream() {
		return list.stream();
	}
	
	public void sort(Comparator<? super E> c) {
		list.sort(c);
	}
	
	public int size() {
		return list.size();
	}
	
}
