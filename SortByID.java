import java.util.Comparator;

public class SortByID implements Comparator<Applicant> {
    @Override
    public int compare(Applicant a1, Applicant a2) {
        return a1.getID().compareTo(a2.getID());
    }
}
