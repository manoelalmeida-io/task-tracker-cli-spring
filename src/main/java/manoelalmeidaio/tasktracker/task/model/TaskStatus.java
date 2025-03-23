package manoelalmeidaio.tasktracker.task.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {

  TODO("TO-DO"),
  IN_PROGRESS("IN PROGRESS"),
  DONE("DONE");

  private final String description;
}
