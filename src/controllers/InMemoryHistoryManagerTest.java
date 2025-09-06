package controllers;

import model.Task;
import model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void addTaskToHistory() {
        Task task = new Task("Test", "Test", Status.NEW);
        task.setId(1);

        historyManager.add(task);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История содержит 1 задачу.");
        assertEquals(task, history.get(0), "Задача в истории соответствует добавленной.");
    }

    @Test
    void historyShouldNotExceedMaxSize() {
        for (int i = 1; i <= 15; i++) {
            Task task = new Task("Task " + i, "Description " + i, Status.NEW);
            task.setId(i);
            historyManager.add(task);
        }

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size(), "История не должна превышать 10 элементов.");
        assertEquals(6, history.get(0).getId(), "Первым должен быть 6й элемент (старые удаляются).");
    }

    @Test
    void duplicateTaskShouldNotCreateNewEntry() {
        Task task = new Task("Test", "Test", Status.NEW);
        task.setId(1);

        historyManager.add(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Дубликаты не должны создавать новые записи.");
    }

    @Test
    void removeTaskFromHistory() {
        Task task1 = new Task("Task 1", "Desc", Status.NEW);
        task1.setId(1);
        Task task2 = new Task("Task 2", "Desc", Status.NEW);
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Должна остаться 1 задача после удаления.");
        assertEquals(task2, history.get(0), "Должна остаться вторая задача.");
    }

    @Test
    void historyShouldPreserveOrder() {
        Task task1 = new Task("Task 1", "Desc", Status.NEW);
        task1.setId(1);
        Task task2 = new Task("Task 2", "Desc", Status.NEW);
        task2.setId(2);
        Task task3 = new Task("Task 3", "Desc", Status.NEW);
        task3.setId(3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size(), "Должно быть 3 задачи в истории.");
        assertEquals(task1, history.get(0), "Первая задача должна быть первой.");
        assertEquals(task2, history.get(1), "Вторая задача должна быть второй.");
        assertEquals(task3, history.get(2), "Третья задача должна быть третьей.");
    }
}