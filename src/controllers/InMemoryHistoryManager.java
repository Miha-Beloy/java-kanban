package controllers;
import model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Task> history = new HashMap<>();
    private final List<Integer> historyOrder = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        remove(task.getId());

        history.put(task.getId(), task);
        historyOrder.add(task.getId());

        if (historyOrder.size() > MAX_HISTORY_SIZE) {
            int oldestId = historyOrder.remove(0);
            history.remove(oldestId);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();
        for (Integer id : historyOrder) {
            result.add(history.get(id));
        }
        return result;
    }

    @Override
    public void remove(int id) {
        if (history.containsKey(id)) {
            history.remove(id);
            historyOrder.remove(Integer.valueOf(id));
        }
    }
}