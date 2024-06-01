
package com.javarivera.com.tareas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TareaConfig {
    private List<Tarea> tasks;

    public TareaConfig() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Tarea task) {
        tasks.add(task);
    }

    public void removeTask(Tarea task) {
        tasks.remove(task);
    }

    public List<Tarea> getTasks() {
        return tasks;
    }

    public List<Tarea> getTasksByDate(Date date) {
        return tasks.stream().filter(task -> task.getDueDate().equals(date)).collect(Collectors.toList());
    }

    public List<Tarea> getTasksByPriority(int priority) {
        return tasks.stream().filter(task -> task.getPriority() == priority).collect(Collectors.toList());
    }

    public List<Tarea> getTasksByTag(String tag) {
        return tasks.stream().filter(task -> {
            for (String t : task.getTags()) {
                if (t.equals(tag)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    public void updateTask(Tarea oldTask, Tarea newTask) {
        int index = tasks.indexOf(oldTask);
        if (index != -1) {
            tasks.set(index, newTask);
        }
    }

    public void markTaskAsCompleted(Tarea task) {
        task.setCompleted(true);
    }
}
