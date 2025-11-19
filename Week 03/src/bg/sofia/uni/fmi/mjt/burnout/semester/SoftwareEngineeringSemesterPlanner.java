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
        int minCredits = plan.minimalAmountOfCredits();

        int totalNeeded = 0;
        for (SubjectRequirement r : requirements) {
            totalNeeded += r.minAmountEnrolled();
        }

        UniversitySubject[] chosen = new UniversitySubject[subjects.length];
        int index = 0, credits = 0;

        for (SubjectRequirement req : requirements) {
            int currEnrolled = 0;
            for (UniversitySubject s : subjects) {
                if (s.category().equals(req.category())) {
                    chosen[index++] = s;
                    credits += s.credits();
                    currEnrolled++;

                    if (currEnrolled == req.minAmountEnrolled()) break;
                }
            }
        }

        // adding more subjects if we didn't reach needed credits
        for (UniversitySubject s : subjects) {
            if (credits >= minCredits) {
                break;
            }

            if (!contains(chosen, index, s)) {
                chosen[index++] = s;
                credits += s.credits();
            }
        }

        UniversitySubject[] result = new UniversitySubject[index];
        for (int i = 0; i < index; i++) {
            result[i] = chosen[i];
        }

        return result;
    }

    private boolean contains(UniversitySubject[] arr, int size, UniversitySubject subject) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == subject) {
                return true;
            }
        }

        return false;
    }
}
