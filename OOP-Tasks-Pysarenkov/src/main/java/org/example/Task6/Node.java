package org.example.Task6;

import java.util.concurrent.atomic.AtomicReference;

public class Node<T> {
    T value;
    AtomicReference<Node<T>> next;

    Node(T value) {
        this.value = value;
        this.next = new AtomicReference<>(null);
    }
}
