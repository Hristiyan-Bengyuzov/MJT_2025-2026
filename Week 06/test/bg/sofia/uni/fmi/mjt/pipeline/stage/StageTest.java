package bg.sofia.uni.fmi.mjt.pipeline.stage;

import bg.sofia.uni.fmi.mjt.pipeline.step.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StageTest {

    private Step<String, Integer> stringToIntStep;
    private Step<Integer, Double> intToDoubleStep;
    private Step<Double, String> doubleToStringStep;

    @BeforeEach
    void setUp() {
        stringToIntStep = mock(Step.class);
        intToDoubleStep = mock(Step.class);
        doubleToStringStep = mock(Step.class);
    }

    @Test
    void testStartShouldCreateStageWithInitialStep() {
        Stage<String, Integer> stage = Stage.start(stringToIntStep);

        assertNotNull(stage, "Stage should be created with initial step.");
    }

    @Test
    void testStartShouldThrowExceptionWhenInitialStepIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Stage.start(null),
                "Starting stage with null step should throw exception.");
    }

    @Test
    void testAddStepShouldAddStepAndUpdateOutputType() {
        when(stringToIntStep.process("5")).thenReturn(5);
        when(intToDoubleStep.process(5)).thenReturn(5.5);

        Stage<String, Double> stage = Stage.start(stringToIntStep)
                .addStep(intToDoubleStep);

        Double result = stage.execute("5");
        assertEquals(5.5, result, "Stage should process steps sequentially and return correct output.");
        verify(stringToIntStep).process("5");
        verify(intToDoubleStep).process(5);
    }

    @Test
    void testAddStepShouldThrowExceptionWhenStepIsNull() {
        Stage<String, Integer> stage = Stage.start(stringToIntStep);
        assertThrows(IllegalArgumentException.class, () -> stage.addStep(null),
                "Adding null step should throw exception.");
    }

    @Test
    void testExecuteShouldProcessSingleStepCorrectly() {
        when(stringToIntStep.process("10")).thenReturn(10);

        Stage<String, Integer> stage = Stage.start(stringToIntStep);
        Integer result = stage.execute("10");

        assertEquals(10, result, "Stage should execute single step correctly.");
        verify(stringToIntStep).process("10");
    }

    @Test
    void testExecuteShouldProcessMultipleStepsSequentially() {
        when(stringToIntStep.process("7")).thenReturn(7);
        when(intToDoubleStep.process(7)).thenReturn(7.7);
        when(doubleToStringStep.process(7.7)).thenReturn("7.7");

        Stage<String, String> stage = Stage.start(stringToIntStep)
                .addStep(intToDoubleStep)
                .addStep(doubleToStringStep);

        String result = stage.execute("7");
        assertEquals("7.7", result, "Stage should process multiple steps sequentially.");
        verify(stringToIntStep).process("7");
        verify(intToDoubleStep).process(7);
        verify(doubleToStringStep).process(7.7);
    }

    @Test
    void testExecuteShouldHandleNullInput() {
        when(stringToIntStep.process(null)).thenReturn(0);

        Stage<String, Integer> stage = Stage.start(stringToIntStep);
        Integer result = stage.execute(null);

        assertEquals(0, result, "Stage should handle null input correctly.");
        verify(stringToIntStep).process(null);
    }

    @Test
    void testExecuteShouldHandleNullOutputBetweenSteps() {
        when(stringToIntStep.process("input")).thenReturn(null);
        when(intToDoubleStep.process(null)).thenReturn(3.14);

        Stage<String, Double> stage = Stage.start(stringToIntStep)
                .addStep(intToDoubleStep);

        Double result = stage.execute("input");
        assertEquals(3.14, result, "Stage should handle null outputs between steps.");
        verify(stringToIntStep).process("input");
        verify(intToDoubleStep).process(null);
    }

    @Test
    void testExecuteShouldMaintainStepOrder() {
        Step<String, String> step1 = mock(Step.class);
        Step<String, String> step2 = mock(Step.class);
        Step<String, String> step3 = mock(Step.class);

        when(step1.process("start")).thenReturn("first");
        when(step2.process("first")).thenReturn("second");
        when(step3.process("second")).thenReturn("third");

        Stage<String, String> stage = Stage.start(step1)
                .addStep(step2)
                .addStep(step3);

        String result = stage.execute("start");
        assertEquals("third", result, "Steps should be executed in order.");
        verify(step1).process("start");
        verify(step2).process("first");
        verify(step3).process("second");
    }
}
