package org.example;

import java.util.List;

public interface XMLParser {
    List<Candy> parseFromFile(String filename);
}
