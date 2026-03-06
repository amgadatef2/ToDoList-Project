
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoApp extends JFrame {
    private TaskFunction manager = new TaskFunction();
    private DefaultTableModel model;
    private JTable table;
    private JTextField fTitle, fDesc, fDate;
    private JComboBox<String> cPrio, cStat;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private int selectedId = -1;

    public TodoApp() {
        setTitle("Professional Task Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel الإدخال ---
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Task Details"));

        fTitle = new JTextField();
        fDesc = new JTextField();
        fDate = new JTextField(LocalDateTime.now().format(dtf));
        cPrio = new JComboBox<>(new String[]{"High", "Medium", "Low"});
        cStat = new JComboBox<>(new String[]{"Pending", "In-progress", "Done"});

        pnlInput.add(new JLabel(" Title:"));
        pnlInput.add(fTitle);
        pnlInput.add(new JLabel(" Description:"));
        pnlInput.add(fDesc);
        pnlInput.add(new JLabel(" Due Date:"));
        pnlInput.add(fDate);
        pnlInput.add(new JLabel(" Priority:"));
        pnlInput.add(cPrio);
        pnlInput.add(new JLabel(" Status:"));
        pnlInput.add(cStat);

        // --- أزرار التحكم ---
        JButton btnAdd = new JButton("Add Task");
        JButton btnUpdate = new JButton("Update Selected");
        JButton btnDelete = new JButton("Delete Selected");

        btnAdd.addActionListener(e -> {
            manager.addTask(fTitle.getText(), fDesc.getText(), LocalDateTime.parse(fDate.getText(), dtf), (String) cPrio.getSelectedItem(), (String) cStat.getSelectedItem());
            refreshTable();
        });

        btnUpdate.addActionListener(e -> {
            if (selectedId != -1) {
                manager.updateTask(selectedId, fTitle.getText(), fDesc.getText(), LocalDateTime.parse(fDate.getText(), dtf), (String) cPrio.getSelectedItem(), (String) cStat.getSelectedItem());
                refreshTable();
                selectedId = -1;
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Select a task from the table first!");
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedId != -1) {
                manager.deleteTask(selectedId);
                refreshTable();
                selectedId = -1;
                clearFields();
            }
        });

        pnlInput.add(btnAdd);
        pnlInput.add(btnUpdate);
        pnlInput.add(btnDelete);
        add(pnlInput, BorderLayout.NORTH);

        // --- الجدول (مع ميزة التلوين) ---
        String[] columns = {"ID", "Title", "Description", "Due Date", "Priority", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        // 🌟 هذا هو الجزء المسؤول عن تمييز المهام المتأخرة باللون الأحمر 🌟
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // جلب قيمة الـ Status من العمود رقم 5
                String status = (String) table.getModel().getValueAt(row, 5);

                if ("OVERDUE".equals(status)) {
                    c.setBackground(new Color(255, 102, 102)); // لون أحمر فاتح
                    c.setForeground(Color.WHITE); // لون النص أبيض
                } else {
                    // لو مش متأخرة، ترجع لألوانها الطبيعية
                    c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                    c.setForeground(isSelected ? table.getSelectionForeground() : Color.BLACK);
                }
                return c;
            }
        });

        // حدث عند الضغط على صف في الجدول
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = (int) model.getValueAt(row, 0);
                fTitle.setText((String) model.getValueAt(row, 1));
                fDesc.setText((String) model.getValueAt(row, 2));
                fDate.setText((String) model.getValueAt(row, 3));
                cPrio.setSelectedItem(model.getValueAt(row, 4));
                cStat.setSelectedItem(model.getValueAt(row, 5));
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🌟 هذا هو التايمر الذي يفحص المهام كل دقيقة (60,000 مللي ثانية) 🌟
        Timer timer = new Timer(5000, e -> {
            boolean changed = manager.checkAndMarkOverdue();
            if (changed) {
                refreshTable(); // تحديث الجدول فوراً
                System.out.println("Task changed to OVERDUE!");
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private void clearFields() {
        fTitle.setText("");
        fDesc.setText("");
        fDate.setText(LocalDateTime.now().format(dtf));
        cPrio.setSelectedIndex(0);
        cStat.setSelectedIndex(0);
    }

    private void refreshTable() {
        model.setRowCount(0);
        Task[] tasks = manager.getTasks();
        for (int i = 0; i < manager.getTaskCount(); i++) {
            model.addRow(new Object[]{
                    tasks[i].getId(), tasks[i].getTitle(), tasks[i].getDescription(),
                    tasks[i].getDate().format(dtf), tasks[i].getPriority(), tasks[i].getStatus()
            });
        }
    }
}