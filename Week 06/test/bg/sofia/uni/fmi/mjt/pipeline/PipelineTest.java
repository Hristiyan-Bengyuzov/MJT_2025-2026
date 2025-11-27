package bg.sofia.uni.fmi.mjt.pipeline;

import bg.sofia.uni.fmi.mjt.pipeline.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PipelineTest {
    private Stage<String, Integer> stringToIntStage;
    private Stage<Integer, Double> intToDoubleStage;
    private Stage<Double, String> doubleToStringStage;

    @BeforeEach
    void setUp() {
        stringToIntStage = mock(Stage.class);
        intToDoubleStage = mock(Stage.class);
        doubleToStringStage = mock(Stage.class);
    }

    @Test
    void testStartShouldCreatePipelineWithInitialStage() {
        Pipeline<String, Integer> pipeline = Pipeline.start(stringToIntStage);

        assertNotNull(pipeline, "Pipeline should be created with initial stage.");
    }

    @Test
    void testStartShouldThrowExceptionWhenInitialStageIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Pipeline.start(null),
                "Starting pipeline with null stage should throw exception.");
    }

    @Test
    void testAddStageShouldAddStageAndUpdateOutputType() {
        when(stringToIntStage.execute("5")).thenReturn(5);
        when(intToDoubleStage.execute(5)).thenReturn(5.5);

        Pipeline<String, Double> pipeline = Pipeline.start(stringToIntStage)
                .addStage(intToDoubleStage);

        Double result = pipeline.execute("5");
        assertEquals(5.5, result, "Pipeline should process stages sequentially and return correct output.");
        verify(stringToIntStage).execute("5");
        verify(intToDoubleStage).execute(5);
    }

    @Test
    void testExecuteShouldReturnCachedResultForSameInput() {
        when(stringToIntStage.execute("10")).thenReturn(10);

        Pipeline<String, Integer> pipeline = new Pipeline<>(List.of(stringToIntStage), new Cache());

        Integer firstResult = pipeline.execute("10");
        Integer secondResult = pipeline.execute("10");

        assertEquals(10, firstResult, "Pipeline should return correct result on first execution.");
        assertEquals(10, secondResult, "Pipeline should return cached result on second execution.");
        verify(stringToIntStage, times(1)).execute("10");
    }


    @Test
    void testExecuteShouldProcessMultipleStagesSequentially() {
        when(stringToIntStage.execute("7")).thenReturn(7);
        when(intToDoubleStage.execute(7)).thenReturn(7.7);
        when(doubleToStringStage.execute(7.7)).thenReturn("7.7");

        Pipeline<String, String> pipeline = Pipeline.start(stringToIntStage)
                .addStage(intToDoubleStage)
                .addStage(doubleToStringStage);

        String result = pipeline.execute("7");
        assertEquals("7.7", result, "Pipeline should execute all stages sequentially and return final output.");
        verify(stringToIntStage).execute("7");
        verify(intToDoubleStage).execute(7);
        verify(doubleToStringStage).execute(7.7);
    }
}
