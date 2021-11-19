package uk.ac.ed.inf;
import java.util.Objects;

public class Grid {
    int i;
    int j;

    public Grid(int i, int j) {
        this.i = i;
        this.j = j;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return i == grid.i &&
                j == grid.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }


}
