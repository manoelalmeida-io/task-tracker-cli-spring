package manoelalmeidaio.tasktracker.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import manoelalmeidaio.tasktracker.task.model.Task;
import manoelalmeidaio.tasktracker.task.model.TaskGroup;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskStorage {

  private final ObjectMapper objectMapper;

  private TaskGroup retrieveTasks() {
    try {
      return this.objectMapper.readValue(new File("tasks.json"), TaskGroup.class);
    } catch (FileNotFoundException e) {
      return new TaskGroup();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Task> list() {
    TaskGroup taskGroup = retrieveTasks();
    return taskGroup.getTasks();
  }

  public Task findById(Long id) {
    TaskGroup taskGroup = retrieveTasks();
    return taskGroup.getTasks().stream()
        .filter(task -> task.getId().equals(id))
        .findFirst().orElseThrow(() -> new RuntimeException("Task not found"));
  }

  public Task add(Task task) {
    try {
      TaskGroup taskGroup = retrieveTasks();
      task.setId(taskGroup.getLastGeneratedId() + 1);
      taskGroup.getTasks().add(task);
      taskGroup.setLastGeneratedId(task.getId());
      this.objectMapper.writeValue(new File("tasks.json"), taskGroup);
      return task;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Task update(Task task) {
    try {
      TaskGroup taskGroup = retrieveTasks();
      for (Task groupTask : taskGroup.getTasks()) {
        if (groupTask.getId().equals(task.getId())) {
          groupTask.setDescription(task.getDescription());
          groupTask.setStatus(task.getStatus());
          groupTask.setUpdatedAt(task.getUpdatedAt());
        }
      }
      this.objectMapper.writeValue(new File("tasks.json"), taskGroup);
      return task;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(Long id) {
    try {
      TaskGroup taskGroup = retrieveTasks();
      taskGroup.getTasks().removeIf(item -> item.getId().equals(id));
      this.objectMapper.writeValue(new File("tasks.json"), taskGroup);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
