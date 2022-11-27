package prog.ua.logic;

public enum IndexArrayListArrays {
    PAST(0),
    CURRENT(1),
    FUTURE(2);

    final int index;

    IndexArrayListArrays(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
