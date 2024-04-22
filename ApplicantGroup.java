

import java.util.ArrayList;

public class ApplicantGroup implements Comparable<ApplicantGroup> {
    private int id;
    private ArrayList<Applicant> groupMembers;
    private int groupSize;

    public ApplicantGroup(int id, Applicant firstGrpMember) {
        this.id = id;
        groupMembers = new ArrayList<>();
        groupMembers.add(firstGrpMember);
        groupSize = 1;
    }

    public void addGrpMember(Applicant grpMember) {
        groupMembers.add(grpMember);
        groupSize = groupMembers.size();
    }

    public int compareTo(ApplicantGroup a) {
        return this.groupSize - a.groupSize;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        ApplicantGroup a = (ApplicantGroup) o;
        if (this.id == a.id)
            return true;
        else
            return false;
    }
}
