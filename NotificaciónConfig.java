
package com.javarivera.com.tareas;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class NotificaciónConfig {
    public void scheduleNotification(Tarea task, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Recordatorio: " + task.getTitle());
                //probar si esta en funcionamiento esta parte!!!!
            }
        }, delay);
    }
}