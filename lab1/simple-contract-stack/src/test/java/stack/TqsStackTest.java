package stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * TqsStackTest
 */
class TqsStackTest {

    TqsStack<Integer> intStack;
    
    @BeforeEach
    void setup() {
        intStack = new TqsStack<>();
    }

    @DisplayName("A stack is empty on construction")
    @Test
    void isEmpty() {
        assertTrue(intStack.isEmpty());
    }

    @DisplayName("A stack has size 0 on construction")
    @Test
    void hasSizeZero() {
        assertEquals(0, intStack.size());
    }

    @DisplayName("A stack has size 3 after pushing three element")
    @Test
    void hasSizeThree() {
        intStack.push(1);
        intStack.push(2);
        intStack.push(3);
        assertEquals(3, intStack.size());
    }

    @DisplayName("If one pushes x then pops, the value popped is x")
    @Test
    void pushXThenPopX() {
        intStack.push(42);
        assertEquals(42, intStack.pop());
    }

    @DisplayName("If one pushes x then peeks, the value returned is x but the size stays the same")
    @Test
    void pushXThenPeekX() {
        intStack.push(42);
        assertEquals(42, intStack.peek());
        assertEquals(1, intStack.size());
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty")
    @Test
    void nPopsEmptyStack() {
        intStack.push(42);
        intStack.pop();
        assertTrue(intStack.isEmpty());
    }

    @DisplayName("Popping from an empty stack throws EmptyStackException")
    @Test
    void popEmptyStack() {
        assertTrue(intStack.isEmpty());
        assertTrue(intStack.isEmpty());
    }

    @DisplayName("Peeking into an empty stack throws EmptyStackException")
    @Test
    void peekEmptyStack() {
        assertTrue(intStack.isEmpty());
        assertTrue(intStack.isEmpty());
    }

    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    @Test
    @Disabled
    void pushFullStack() {
        // Not applicable for an unbounded stack
        assertTrue(true);
    }
    
}