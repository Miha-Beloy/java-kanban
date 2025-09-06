package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void epicsWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic("Epic 1", "Description 1");
        epic1.setId(1);
        Epic epic2 = new Epic("Epic 2", "Description 2");
        epic2.setId(1);

        assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны.");
    }

    @Test
    void epicShouldHaveNewStatusWhenNoSubtasks() {
        Epic epic = new Epic("Epic", "Description");

        assertEquals(Status.NEW, epic.getStatus(), "Эпик без подзадач должен иметь статус NEW.");
    }

    @Test
    void epicShouldAddAndRemoveSubtaskIds() {
        Epic epic = new Epic("Epic", "Description");
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);

        assertEquals(2, epic.getSubtaskIds().size(), "Эпик должен содержать 2 id подзадач.");

        epic.removeSubtaskId(1);
        assertEquals(1, epic.getSubtaskIds().size(), "Эпик должен содержать 1 id после удаления.");

        epic.clearSubtaskIds();
        assertTrue(epic.getSubtaskIds().isEmpty(), "Эпик должен быть пуст после очистки.");
    }
}