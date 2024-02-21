package stack;
import java.util.EmptyStackException;
import java.util.LinkedList;

class TqsStack<T> {
    private LinkedList<T> stack;

    public TqsStack() {
        stack = new LinkedList<>();
    }

    public void push(T item) {
        stack.addFirst(item);
    }

    public T pop() {
        if (!isEmpty()) {
            return stack.removeFirst();
        } else {
            throw new EmptyStackException();
        }
    }

    public T peek() {
        if (!isEmpty()) {
            return stack.getFirst();
        } else {
            throw new EmptyStackException();
        }
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}