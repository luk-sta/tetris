package cz.hyperion;

import cz.hyperion.model.Figure;

import java.util.function.Function;

final class FigureAction {
    private final Function<Figure, Figure> actionFunction;
    private int counter = 6;

    FigureAction(Function<Figure, Figure> actionFunction) {
        this.actionFunction = actionFunction;
    }

    Figure apply(Figure figure) {
        counter--;
        return actionFunction.apply(figure);
    }

    boolean isStale() {
        return counter <= 0;
    }
}
