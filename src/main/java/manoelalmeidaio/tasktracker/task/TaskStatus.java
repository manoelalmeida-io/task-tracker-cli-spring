package manoelalmeidaio.tasktracker.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {

  IN_PROGRESS("IN PROGRESS"),
  DONE("DONE");

  private final String description;
}
