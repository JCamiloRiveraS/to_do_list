
package com.javarivera.com.tareas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.awt.Image;
import java.awt.Toolkit;

public class TaskUI {
    private JFrame frame;
    private TareaConfig taskManager;
    private NotificaciónConfig notificationManager;
    private JTable taskTable;
    private DefaultTableModel tableModel;

    public TaskUI() {
        taskManager = new TareaConfig();
        notificationManager = new NotificaciónConfig();
        initialize();
    }
//inicializar
    private void initialize() {
        //caracteristicas de la ventana
        frame = new JFrame("Gestor de Tareas Formulación de Proyectos JAVA");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(238, 238, 238));
        //crear el jpanel
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 255, 255));

        // Modelo de la tabla (columnas)
        String[] columnNames = {"Tarea", "Descripción", "Días Restantes", "Prioridad", "Etiquetas", "Completada"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBackground(new Color(255, 255, 255));
        
        //btn agregar tarea
        JButton addButton = new JButton("Agregar Tarea");
        addButton.setBackground(new Color( 229, 190, 1));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        buttonPanel.add(addButton);
        //btn actualizar tarea
        JButton updateButton = new JButton("Actualizar Tarea");
        updateButton.setBackground(new Color(0, 123, 255));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTask();
            }
        });
        buttonPanel.add(updateButton);
        // btn eliminar tarea
        JButton deleteButton = new JButton("Eliminar Tarea");
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });
        buttonPanel.add(deleteButton);
        //btn mostrar tarea (falta configurar)
        JButton showButton = new JButton("Mostrar Tareas");
        showButton.setBackground(new Color(40, 167, 69));
        showButton.setForeground(Color.WHITE);
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTasks();
            }
        });
        buttonPanel.add(showButton);
    }
    //imagen de logo de  ventana
    
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit() .getImage(ClassLoader.getSystemResource("\"C:\\Users\\USUARIO\\Pictures\\yosoyfet.jpg\""));
        return retValue;
    }
//añadir tarea
    private void addTask() {
        //tabla               
                
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        JTextField titleField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField daysRemainingField = new JTextField();
        JTextField priorityField = new JTextField();
        JTextField tagsField = new JTextField();

        panel.add(new JLabel("Título de la Tarea:"));
        panel.add(titleField);
        panel.add(new JLabel("Descripción:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Días Restantes:"));
        panel.add(daysRemainingField);
        panel.add(new JLabel("Prioridad (1-5):"));
        panel.add(priorityField);
        panel.add(new JLabel("Etiquetas (separadas por comas):"));
        panel.add(tagsField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Añadir una Nueva Tarea", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String description = descriptionField.getText();
            int daysRemaining = Integer.parseInt(daysRemainingField.getText());
            int priority = Integer.parseInt(priorityField.getText());
            String tagsString = tagsField.getText();
            String[] tags = tagsString.split(",");

            Date dueDate = new Date(System.currentTimeMillis() + (long) daysRemaining * 24 * 60 * 60 * 1000);

            Tarea task = new Tarea(title, description, dueDate, priority, tags);
            taskManager.addTask(task);

            // Establecer notificación 
            long delay = dueDate.getTime() - System.currentTimeMillis();
            if (delay > 0) {
                notificationManager.scheduleNotification(task, delay);
            }

            JOptionPane.showMessageDialog(frame, "la tarea fué añadida correctamente.");
            showTasks();
        }
    }
    
    //actualizar tarea

    private void updateTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Primero debes selecciona una tarea para actualizar.");
            return;
        }
        //tarea vieja
        Tarea oldTask = taskManager.getTasks().get(selectedRow);
        //tarea nueva
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        JTextField titleField = new JTextField(oldTask.getTitle());
        JTextField descriptionField = new JTextField(oldTask.getDescription());
        JTextField daysRemainingField = new JTextField(String.valueOf(oldTask.getDaysRemaining()));
        JTextField priorityField = new JTextField(String.valueOf(oldTask.getPriority()));
        JTextField tagsField = new JTextField(String.join(",", oldTask.getTags()));

        panel.add(new JLabel("Nuevo Título:"));
        panel.add(titleField);
        panel.add(new JLabel("Nueva Descripción:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Nuevos Días Restantes:"));
        panel.add(daysRemainingField);
        panel.add(new JLabel("Nueva Prioridad (1-5):"));
        panel.add(priorityField);
        panel.add(new JLabel("Nuevas Etiquetas (separadas por comas):"));
        panel.add(tagsField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Tarea", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String description = descriptionField.getText();
            int daysRemaining = Integer.parseInt(daysRemainingField.getText());
            int priority = Integer.parseInt(priorityField.getText());
            String tagsString = tagsField.getText();
            String[] tags = tagsString.split(",");

            Date dueDate = new Date(System.currentTimeMillis() + (long) daysRemaining * 24 * 60 * 60 * 1000);

            Tarea newTask = new Tarea(title, description, dueDate, priority, tags);
            taskManager.updateTask(oldTask, newTask);

            JOptionPane.showMessageDialog(frame, "la Tarea fué actualizada correctamente.");
            showTasks();
        }
    }
//borrar tareasde la tabla
    private void deleteTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Primero debes selecciona una tarea a eliminar.");
            return;
        }

        Tarea task = taskManager.getTasks().get(selectedRow);
        taskManager.removeTask(task);

        JOptionPane.showMessageDialog(frame, "la tarea fué eliminada correctamente.");
        showTasks();
    }
//mostrar y ocultar datos de la tabla
    private void showTasks() {
        tableModel.setRowCount(0); 
// no cumple su funcion, verificar que componentes son incompatibles
        List<Tarea> tasks = taskManager.getTasks();

        for (Tarea task : tasks) {
            Object[] rowData = {
                    task.getTitle(),
                    task.getDescription(),
                    task.getDaysRemaining(),
                    task.getPriority(),
                    String.join(", ", task.getTags()),
                    task.isCompleted() ? "Sí" : "No"
            };
            tableModel.addRow(rowData);
        }
    }

    public void show() {
        frame.setVisible(true);
    }
}
