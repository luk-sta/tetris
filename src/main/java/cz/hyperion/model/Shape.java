package cz.hyperion.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;

public abstract class Shape {
    protected List<Element> elements;

    protected Shape(List<Element> elements) {
        this.elements = elements;
    }

    protected Shape(Element... elements) {
        this(List.of(elements));
    }

    protected Element e0() {
        return elements.get(0);
    }

    protected Element e1() {
        return elements.get(1);
    }

    protected Element e2() {
        return elements.get(2);
    }

    protected Element e3() {
        return elements.get(3);
    }

    protected List<Element> transform(ElementShift... elementShifts) {
        return List.of(
                new Element(e0(), elementShifts[0]),
                new Element(e1(), elementShifts[1]),
                new Element(e2(), elementShifts[2]),
                new Element(e3(), elementShifts[3])
        );
    }

    public Shape moveDown() {
        return move(Element::moveDown);
    }

    public Shape moveLeft() {
        return move(Element::moveLeft);
    }

    public Shape moveRight() {
        return move(Element::moveRight);
    }

    private List<Element> moveElements(Function<Element, Element> f) {
        return elements.stream().map(f).toList();
    }

    private Shape move(Function<Element, Element> f) {
        try {
            List<Element> elements = moveElements(f);
            Constructor constructor = this.getClass().getConstructor(List.class);
            return (Shape) constructor.newInstance(elements);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Shape rotateLeft();

    public abstract Shape rotateRight();
}
