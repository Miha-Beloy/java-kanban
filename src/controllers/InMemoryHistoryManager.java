package controllers;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        // Удаляем старую версию если есть
        history.removeIf(t -> t.getId() == task.getId());

        // Добавляем новую
        history.add(task);

        // Ограничиваем размер
        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void remove(int id) {
        history.removeIf(task -> task.getId() == id);
    }
}