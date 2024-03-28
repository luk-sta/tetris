package cz.hyperion;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

final class BodyDefinitionReader {
    static List<Body> readBodies(String csvFileName) throws IOException {
        final URL definitionCsvUrl = ClassLoader.getSystemResource(csvFileName);
        List<Body> bodies = new ArrayList<>();
        try (var inputStream = definitionCsvUrl.openStream(); Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine().trim();
                if (line.startsWith("#") || line.startsWith("//") || line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(",[ ]*");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                double vx = Double.parseDouble(parts[2]);
                double vy = Double.parseDouble(parts[3]);
                double mass = Double.parseDouble(parts[4]);
                Color color = Color.decode(parts[5].trim());
                bodies.add(new Body(x, y, vx, vy, mass, color));
            }
        }
        return bodies;
    }
}
