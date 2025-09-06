package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", Status.NEW, 1);
        subtask1.setId(1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", Status.DONE, 2);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым id должны быть равны.");
    }

    @Test
    void subtaskShouldReturnCorrectEpicId() {
        Subtask subtask = new Subtask("Test", "Test", Status.NEW, 5);
        assertEquals(5, subtask.getEpicId(), "Подзадача должна возвращать правильный epicId.");
    }

    @Test
    void subtaskShouldSetEpicId() {
        Subtask subtask = new Subtask("Test", "Test", Status.NEW, 5);
        subtask.setEpicId(10);
        assertEquals(10, subtask.getEpicId(), "Подзадача должна устанавливать новый epicId.");
    }
}