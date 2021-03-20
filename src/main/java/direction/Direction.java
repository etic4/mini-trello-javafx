package direction;

public enum Direction {
    LEFT("gauche"), RIGHT("droite"), UP("haut"), DOWN("bas");

    private final String label;

    Direction(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
