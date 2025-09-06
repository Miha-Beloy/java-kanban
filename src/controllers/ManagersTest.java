package controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultShouldReturnTaskManagerInstance() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Менеджер не должен быть null");
        assertTrue(manager instanceof InMemoryTaskManager, "Должен возвращаться InMemoryTaskManager");
    }

    @Test
    void getDefaultHistoryShouldReturnHistoryManagerInstance() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер истории не должен быть null");
        assertTrue(historyManager instanceof InMemoryHistoryManager, "Должен возвращаться InMemoryHistoryManager");
    }

    @Test
    void managersShouldReturnDifferentInstances() {
        TaskManager manager1 = Managers.getDefault();
        TaskManager manager2 = Managers.getDefault();
        assertNotSame(manager1, manager2, "Должны возвращаться разные экземпляры менеджера");
    }
}