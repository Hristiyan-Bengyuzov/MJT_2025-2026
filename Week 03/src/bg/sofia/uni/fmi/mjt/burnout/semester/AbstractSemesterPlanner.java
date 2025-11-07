package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.DisappointmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public abstract sealed class AbstractSemesterPlanner implements SemesterPlannerAPI permits ComputerScienceSemesterPlanner, SoftwareEngineeringSemesterPlanner {
    private static final int DAYS_PER_JAR = 5;

    @Override
    public final UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan)
            throws InvalidSubjectRequirementsException {
        validatePlan(semesterPlan);
        UniversitySubject[] subjects = semesterPlan.subjects();

        sortSubjects(subjects);
        UniversitySubject[] chosen = selectSubjects(semesterPlan, subjects);

        if (!canCoverCredits(chosen, semesterPlan.minimalAmountOfCredits())) {
            throw new CryToStudentsDepartmentException("Cannot cover credits.");
        }

        return chosen;
    }

    @Override
    public int calculateJarCount(UniversitySubject[] subjects, int maximumSlackTime, int semesterDuration) throws DisappointmentException {
        if (subjects == null || subjects.length == 0) {
            throw new IllegalArgumentException("Subjects can't be null or missing.");
        }

        if (maximumSlackTime <= 0) {
            throw new IllegalArgumentException("Maximum slack time must be positive.");
        }

        if (semesterDuration <= 0) {
            throw new IllegalArgumentException("Semester duration must be positive.");
        }

        int totalRestTime = 0;
        int totalStudyTime = 0;

        for (UniversitySubject subject : subjects) {
            totalStudyTime += subject.neededStudyTime();
            totalRestTime += (int) Math.ceil(subject.neededStudyTime() * subject.category().getCoefficient());
        }

        if (totalRestTime > maximumSlackTime) {
            throw new DisappointmentException("Grandma is disappointed in you.");
        }

        int totalTime = totalStudyTime + totalRestTime;
        int jars = totalStudyTime / DAYS_PER_JAR;

        return totalTime > semesterDuration ? jars * 2 : jars;
    }

    protected void validatePlan(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException {
        if (semesterPlan == null) {
            throw new IllegalArgumentException("Semester plan cannot be null.");
        }

        SubjectRequirement[] requirements = semesterPlan.subjectRequirements();

        for (int i = 0; i < requirements.length - 1; i++) {
            for (int j = i + 1; j < requirements.length; j++) {
                if (requirements[i].category().equals(requirements[j].category())) {
                    throw new InvalidSubjectRequirementsException("There is a duplicate category in requirements.");
                }
            }
        }
    }

    protected boolean canCoverCredits(UniversitySubject[] subjects, int neededCredits) {
        int credits = 0;

        for (UniversitySubject subject : subjects) {
            credits += subject.credits();
        }

        return credits >= neededCredits;
    }

    protected abstract void sortSubjects(UniversitySubject[] subjects);
    protected abstract UniversitySubject[] selectSubjects(SemesterPlan plan, UniversitySubject[] subjects);
}
