package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public final class ComputerScienceSemesterPlanner extends AbstractSemesterPlanner {

    @Override
    protected void sortSubjects(UniversitySubject[] subjects) {
        // Rating descending
        for (int i = 0; i < subjects.length - 1; i++) {
            for (int j = 0; j < subjects.length - i - 1; j++) {
                if (subjects[j].rating() < subjects[j + 1].rating()) {
                    UniversitySubject temp = subjects[j];
                    subjects[j] = subjects[j + 1];
                    subjects[j + 1] = temp;
                }
            }
        }
    }

    @Override
    protected UniversitySubject[] selectSubjects(SemesterPlan plan, UniversitySubject[] subjects) {
        int totalCredits = 0;
        int count = 0;

        for (UniversitySubject s : subjects) {
            totalCredits += s.credits();
            count++;
            if (totalCredits >= plan.minimalAmountOfCredits()) break;
        }

        UniversitySubject[] chosen = new UniversitySubject[count];
        for (int i = 0; i < count; i++) {
            chosen[i] = subjects[i];
        }

        return chosen;
    }
}
