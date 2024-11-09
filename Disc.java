import java.io.Serializable;

public class Disc implements Serializable {
    private String color;

    public Disc(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color.equals("Red") ? "R" : "G";
    }
}
