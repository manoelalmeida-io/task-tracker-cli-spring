package manoelalmeidaio.tasktracker.task;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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
}
