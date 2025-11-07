package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public final class SoftwareEngineeringSemesterPlanner extends AbstractSemesterPlanner {
    @Override
    protected void sortSubjects(UniversitySubject[] subjects) {
        // Credits descending
        for (int i = 0; i < subjects.length - 1; i++) {
            for (int j = 0; j < subjects.length - i - 1; j++) {
                if (subjects[j].credits() < subjects[j + 1].credits()) {
                    UniversitySubject temp = subjects[j];
                    subjects[j] = subjects[j + 1];
                    subjects[j + 1] = temp;
                }
            }
        }
    }

    @Override
    protected UniversitySubject[] selectSubjects(SemesterPlan plan, UniversitySubject[] subjects) {
        SubjectRequirement[] requirements = plan.subjectRequirements();

        int totalNeeded = 0;
        for (SubjectRequirement r : requirements) {
            totalNeeded += r.minAmountEnrolled();
        }

        UniversitySubject[] chosen = new UniversitySubject[totalNeeded];
        int index = 0;

        for (SubjectRequirement req : requirements) {
            int count = 0;
            for (UniversitySubject s : subjects) {
                if (s.category().equals(req.category())) {
                    chosen[index++] = s;
                    count++;
                    if (count == req.minAmountEnrolled()) break;
                }
            }
        }

        return chosen;
    }
}
