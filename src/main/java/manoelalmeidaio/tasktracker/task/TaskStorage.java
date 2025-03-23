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
}
