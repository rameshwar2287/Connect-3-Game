import java.io.Serializable;

public class Square implements Serializable {
    private Disc disc;

    public boolean isEmpty() {
        return disc == null;
    }

    public void setDisc(Disc disc) {
        this.disc = disc;
    }

    public Disc getDisc() {
        return disc;
    }

    @Override
    public String toString() {
        return disc == null ? "-" : disc.toString();
    }
}
