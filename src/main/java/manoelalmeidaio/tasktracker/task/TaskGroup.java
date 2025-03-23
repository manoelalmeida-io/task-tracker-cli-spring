package manoelalmeidaio.tasktracker.task;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskGroup {

  private Long lastGeneratedId = 0L;
  private List<Task> tasks = new ArrayList<>();
}
