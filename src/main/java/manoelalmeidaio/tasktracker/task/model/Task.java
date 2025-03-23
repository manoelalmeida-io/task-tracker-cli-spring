package manoelalmeidaio.tasktracker.task.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Task {

  private Long id;
  private String description;
  private TaskStatus status;
  private LocalDateTime createAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  public Task() {
  }

  public Task(String description) {
    this.description = description;
    this.status = TaskStatus.IN_PROGRESS;
  }

  @Override
  public String toString() {
    return """
        |%3d| %-50.50s| %-11s| %-16s| %-16s|
        """.formatted(id, description, status.getDescription(),
            formatter.format(createAt), formatter.format(updatedAt));
  }
}
