package manoelalmeidaio.tasktracker.task;

import lombok.RequiredArgsConstructor;
import manoelalmeidaio.tasktracker.task.model.Task;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDateTime;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class TaskCommands {

  private final TaskStorage taskStorage;

  @ShellMethod(key = "list")
  public String list() {
    List<Task> tasks = this.taskStorage.list();
    StringBuilder message = new StringBuilder();
    message.append("""
        |%3.3s| %-50.50s| %-11s| %-16s| %-16s|
        +---+---------------------------------------------------+------------+-----------------+-----------------+
        """.formatted("ID", "DESCRIPTION", "STATUS", "CREATED_AT", "UPDATED_AT"));
    for (Task task : tasks) {
      message.append(task);
    }
    return message.toString();
  }

  @ShellMethod(key = "add")
  public String add(@ShellOption String description) {
    Task task = new Task(description);
    task = this.taskStorage.add(task);
    return "Task added successfully (ID: %d)".formatted(task.getId());
  }

  @ShellMethod(key = "update")
  public String update(@ShellOption Long id, @ShellOption String description) {
    Task task = this.taskStorage.findById(id);
    task.setDescription(description);
    task.setUpdatedAt(LocalDateTime.now());
    task = this.taskStorage.update(task);
    return "Task updated successfully (ID: %d)".formatted(task.getId());
  }

  @ShellMethod(key = "delete")
  public String delete(@ShellOption Long id) {
    this.taskStorage.delete(id);
    return "Task deleted successfully (ID: %d)".formatted(id);
  }
}
