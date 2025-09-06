package controllers;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    void createdTaskShouldBeFoundById() {
        Task task = new Task("Test", "Test", Status.NEW);
        Task createdTask = taskManager.createTask(task);

        Task foundTask = taskManager.getTaskById(createdTask.getId());

        assertNotNull(foundTask, "Задача должна находиться по id.");
        assertEquals(createdTask, foundTask, "Найденная задача должна совпадать с созданной.");
    }

    @Test
    void createdEpicShouldBeFoundById() {
        Epic epic = new Epic("Test Epic", "Test");
        Epic createdEpic = taskManager.createEpic(epic);

        Epic foundEpic = taskManager.getEpicById(createdEpic.getId());

        assertNotNull(foundEpic, "Эпик должен находиться по id.");
        assertEquals(createdEpic, foundEpic, "Найденный эпик должен совпадать с созданным.");
    }

    @Test
    void createdSubtaskShouldBeFoundById() {
        Epic epic = taskManager.createEpic(new Epic("Parent Epic", "Test"));
        Subtask subtask = new Subtask("Test Subtask", "Test", Status.NEW, epic.getId());
        Subtask createdSubtask = taskManager.createSubtask(subtask);

        Subtask foundSubtask = taskManager.getSubtaskById(createdSubtask.getId());

        assertNotNull(foundSubtask, "Подзадача должна находиться по id.");
        assertEquals(createdSubtask, foundSubtask, "Найденная подзадача должна совпадать с созданной.");
    }

    @Test
    void epicShouldNotAddItselfAsSubtask() {
        Epic epic = taskManager.createEpic(new Epic("Epic", "Test"));

        Subtask subtask = new Subtask("Subtask", "Test", Status.NEW, epic.getId());
        subtask.setId(epic.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createSubtask(subtask);
        }, "Эпик не должен добавлять себя в качестве подзадачи.");
    }

    @Test
    void taskShouldRemainUnchangedAfterAddingToManager() {
        Task originalTask = new Task("Original", "Original", Status.NEW);
        Task createdTask = taskManager.createTask(originalTask);

        assertEquals("Original", createdTask.getTitle(), "Название задачи не должно измениться.");
        assertEquals("Original", createdTask.getDescription(), "Описание задачи не должно измениться.");
        assertEquals(Status.NEW, createdTask.getStatus(), "Статус задачи не должен измениться.");
        assertTrue(createdTask.getId() > 0, "Задаче должен быть присвоен id.");
    }

    @Test
    void deleteTaskShouldRemoveItFromManager() {
        Task task = taskManager.createTask(new Task("Test", "Description", Status.NEW));
        taskManager.deleteTaskById(task.getId());

        assertNull(taskManager.getTaskById(task.getId()), "Задача должна быть удалена.");
    }

    @Test
    void deleteEpicShouldRemoveItsSubtasks() {
        Epic epic = taskManager.createEpic(new Epic("Test Epic", "Description"));
        Subtask subtask = taskManager.createSubtask(
                new Subtask("Test Subtask", "Description", Status.NEW, epic.getId())
        );

        taskManager.deleteEpicById(epic.getId());

        assertNull(taskManager.getEpicById(epic.getId()), "Эпик должен быть удален.");
        assertNull(taskManager.getSubtaskById(subtask.getId()), "Подзадача эпика должна быть удалена.");
    }

    @Test
    void getAllTasksShouldReturnAllCreatedTasks() {
        Task task1 = taskManager.createTask(new Task("Task 1", "Desc", Status.NEW));
        Task task2 = taskManager.createTask(new Task("Task 2", "Desc", Status.NEW));

        assertEquals(2, taskManager.getAllTasks().size(), "Должно быть 2 задачи.");
        assertTrue(taskManager.getAllTasks().contains(task1), "Список должен содержать первую задачу.");
        assertTrue(taskManager.getAllTasks().contains(task2), "Список должен содержать вторую задачу.");
    }

    @Test
    void updateTaskShouldChangeItsProperties() {
        Task task = taskManager.createTask(new Task("Original", "Desc", Status.NEW));
        task.setTitle("Updated");
        task.setStatus(Status.DONE);
        taskManager.updateTask(task);

        Task updated = taskManager.getTaskById(task.getId());
        assertEquals("Updated", updated.getTitle(), "Название должно обновиться.");
        assertEquals(Status.DONE, updated.getStatus(), "Статус должен обновиться.");
    }

    @Test
    void getSubtasksByEpicIdShouldReturnCorrectList() {
        Epic epic = taskManager.createEpic(new Epic("Epic", "Desc"));
        Subtask subtask1 = taskManager.createSubtask(
                new Subtask("Sub 1", "Desc", Status.NEW, epic.getId())
        );
        Subtask subtask2 = taskManager.createSubtask(
                new Subtask("Sub 2", "Desc", Status.NEW, epic.getId())
        );

        assertEquals(2, taskManager.getSubtasksByEpicId(epic.getId()).size(), "Должно быть 2 подзадачи.");
        assertTrue(taskManager.getSubtasksByEpicId(epic.getId()).contains(subtask1), "Должна быть первая подзадача.");
        assertTrue(taskManager.getSubtasksByEpicId(epic.getId()).contains(subtask2), "Должна быть вторая подзадача.");
    }

    @Test
    void historyShouldContainViewedTasks() {
        Task task = taskManager.createTask(new Task("Test", "Test", Status.NEW));
        Epic epic = taskManager.createEpic(new Epic("Epic", "Test"));

        taskManager.getTaskById(task.getId());
        taskManager.getEpicById(epic.getId());

        List<Task> history = taskManager.getHistory();

        assertEquals(2, history.size(), "История должна содержать 2 просмотренные задачи.");
        assertTrue(history.contains(task), "История должна содержать просмотренную задачу.");
        assertTrue(history.contains(epic), "История должна содержать просмотренный эпик.");
    }
}