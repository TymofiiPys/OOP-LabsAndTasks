package org.example.Task6;

import java.util.concurrent.atomic.AtomicReference;

public class MSQueue<T> {
    private AtomicReference<Node<T>> Head;
    private AtomicReference<Node<T>> Tail;

    public MSQueue() {
        Node<T> node = new Node<>(null);
        Head = new AtomicReference<>(node);
        Tail = new AtomicReference<>(node);
    }

    public void enqueue(T value) {
        Node<T> node = new Node<>(value);

        while (true) {
            Node<T> tail = Tail.get();
            Node<T> next = tail.next.get();

            if (tail == Tail.get()) {
                if (next == null) {
                    if (tail.next.compareAndSet(next, node)) {
                        Tail.compareAndSet(tail, node);
                        break;
                    }
                } else {
                    Tail.compareAndSet(tail, next);
                }
            }
        }
    }

    public boolean dequeue(/*AtomicReference<T> pvalue*/) {
        while (true) {
            Node<T> head = Head.get();
            Node<T> tail = Tail.get();
            Node<T> next = head.next.get();

            if (head == Head.get()) {
                if (head == tail) {
                    if (next == null) {
                        return false; // Queue is empty
                    }
                    Tail.compareAndSet(tail, next);
                } else {
//                        pvalue.set(next.value);
                    if (Head.compareAndSet(head, next)) {
                        return true;
                    }
                }
            }
        }
    }
}

