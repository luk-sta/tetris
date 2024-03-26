package cz.hyperion.model;

import java.util.List;
import java.util.function.Function;

public class Shape {

    private final Element pivotElement;

    private final List<ElementShift> otherElementPlacements;

    private final List<Element> elements;

    public Shape(Element pivotElement, List<ElementShift> otherElementPlacements) {
        this.pivotElement = pivotElement;
        this.otherElementPlacements = otherElementPlacements;
        this.elements = List.of(
                pivotElement,
                new Element(pivotElement, otherElementPlacements.get(0)),
                new Element(pivotElement, otherElementPlacements.get(1)),
                new Element(pivotElement, otherElementPlacements.get(2))
        );
    }

    public Shape(Element pivotElement, ElementShift... otherElementPlacements) {
        this(pivotElement, List.of(otherElementPlacements));
    }

    public List<Element> getElements() {
        return elements;
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

    private Shape move(Function<Element, Element> f) {
        return new Shape(f.apply(pivotElement), otherElementPlacements);
    }

    public Shape rotateLeft() {
        return rotate(this::rotateLeft);
    }

    public Shape rotateRight() {
        return rotate(this::rotateRight);
    }

    private Shape rotate(Function<ElementShift, ElementShift> f) {
        return new Shape(pivotElement, otherElementPlacements.stream().map(f).toList());
    }

    private ElementShift rotateLeft(ElementShift elementShift) {
        return new ElementShift(elementShift.down(), -1 * elementShift.right());
    }

    private ElementShift rotateRight(ElementShift elementShift) {
        return new ElementShift(-1 * elementShift.down(), elementShift.right());
    }

}
