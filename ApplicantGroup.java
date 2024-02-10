public class ApplicantGroup {
    private int id;
    private Applicant[] groupMembers;

    public ApplicantGroup(int id, Applicant[] groupMembers) {
        this.id = id;
        this.groupMembers = groupMembers;

        //assigns a character ID to each member of the group, starting at A
        //so the first member of group 1 will be applicant 1-A, second member will be 1-B, etc.
        //Sort of like Group 802-C!
        for (int i = 0; i < groupMembers.length; i++) {
            groupMembers[i].createID(i);
        }
    }

}
