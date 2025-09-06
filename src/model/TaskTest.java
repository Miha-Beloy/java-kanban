package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("Title", "Description", Status.NEW);
        task1.setId(1);
        Task task2 = new Task("Different Title", "Different Description", Status.DONE);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны.");
    }

    @Test
    void taskShouldNotBeEqualToNull() {
        Task task = new Task("Title", "Description", Status.NEW);
        task.setId(1);

        assertNotEquals(null, task, "Задача не должна быть равна null.");
    }

    @Test
    void taskShouldBeEqualToItself() {
        Task task = new Task("Title", "Description", Status.NEW);
        task.setId(1);

        assertEquals(task, task, "Задача должна быть равна самой себе.");
    }

    @Test
    void taskWithDifferentIdShouldNotBeEqual() {
        Task task1 = new Task("Title", "Description", Status.NEW);
        task1.setId(1);
        Task task2 = new Task("Title", "Description", Status.NEW);
        task2.setId(2);

        assertNotEquals(task1, task2, "Задачи с разным id не должны быть равны.");
    }
}