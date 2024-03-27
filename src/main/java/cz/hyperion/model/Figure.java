package cz.hyperion.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Figure {

    private final Element pivotElement;

    private final Collection<ElementShift> otherElementPlacements;

    private final List<Element> elements;

    public Figure(Element pivotElement, Collection<ElementShift> otherElementPlacements) {
        this.pivotElement = pivotElement;
        this.otherElementPlacements = otherElementPlacements;
        this.elements = new LinkedList<>(List.of(pivotElement));
        elements.addAll(otherElementPlacements.stream().map(es -> new Element(pivotElement, es)).toList());
    }

    public Figure(Element pivotElement, ElementShift... otherElementPlacements) {
        this(pivotElement, List.of(otherElementPlacements));
    }

    public List<Element> getElements() {
        return elements;
    }

    public Figure moveDown() {
        return move(Element::moveDown);
    }

    public Figure moveLeft() {
        return move(Element::moveLeft);
    }

    public Figure moveRight() {
        return move(Element::moveRight);
    }

    private Figure move(Function<Element, Element> f) {
        return new Figure(f.apply(pivotElement), otherElementPlacements);
    }

    public Figure rotateLeft() {
        return rotate(this::rotateLeft);
    }

    public Figure rotateRight() {
        return rotate(this::rotateRight);
    }

    private Figure rotate(Function<ElementShift, ElementShift> f) {
        return new Figure(pivotElement, otherElementPlacements.stream().map(f).toList());
    }

    private ElementShift rotateLeft(ElementShift elementShift) {
        return new ElementShift(elementShift.down(), -1 * elementShift.right());
    }

    private ElementShift rotateRight(ElementShift elementShift) {
        return new ElementShift(-1 * elementShift.down(), elementShift.right());
    }

}
