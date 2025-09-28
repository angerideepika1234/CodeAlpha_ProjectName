import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Student {
    String name;
    ArrayList<Integer> marks;

    Student(String name) {
        this.name = name;
        marks = new ArrayList<>();
    }

    void addMark(int m) {
        marks.add(m);
    }

    void changeMark(int pos, int m) {
        if (pos >= 0 && pos < marks.size()) {
            marks.set(pos, m);
        }
    }

    double average() {
        if (marks.size() == 0) return 0;
        int total = 0;
        for (int x : marks) total += x;
        return (double) total / marks.size();
    }

    int highest() {
        if (marks.size() == 0) return 0;
        int h = marks.get(0);
        for (int x : marks) if (x > h) h = x;
        return h;
    }

    int lowest() {
        if (marks.size() == 0) return 0;
        int l = marks.get(0);
        for (int x : marks) if (x < l) l = x;
        return l;
    }
}

public class StudentGradeManagerGUI extends JFrame {
    ArrayList<Student> list = new ArrayList<>();
    JTextArea output;

    StudentGradeManagerGUI() {
        setTitle("Grade Manager");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        JButton b1 = new JButton("Add Student");
        JButton b2 = new JButton("Add/Update Grade");
        JButton b3 = new JButton("Report");
        JButton b4 = new JButton("Summary");

        p.add(b1); p.add(b2); p.add(b3); p.add(b4);
        add(p, BorderLayout.NORTH);

        output = new JTextArea();
        add(new JScrollPane(output));

        b1.addActionListener(e -> addStudent());
        b2.addActionListener(e -> addOrChangeMark());
        b3.addActionListener(e -> report());
        b4.addActionListener(e -> summary());
    }

    void addStudent() {
        String n = JOptionPane.showInputDialog(this,"Enter student name:");
        if (n != null && !n.isEmpty()) {
            list.add(new Student(n));
            output.append("Added: " + n + "\n");
        }
    }

    void addOrChangeMark() {
        if (list.size() == 0) {
            JOptionPane.showMessageDialog(this,"No students yet");
            return;
        }
        String[] arr = list.stream().map(s -> s.name).toArray(String[]::new);
        String who = (String) JOptionPane.showInputDialog(this,"Pick student:","",JOptionPane.QUESTION_MESSAGE,null,arr,arr[0]);
        if (who == null) return;
        for (Student s : list) {
            if (s.name.equals(who)) {
                String[] opts = {"Add","Update"};
                int ch = JOptionPane.showOptionDialog(this,"What do you want to do?","",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,opts,opts[0]);
                if (ch == 0) {
                    String g = JOptionPane.showInputDialog(this,"Enter grade:");
                    try {
                        int mark = Integer.parseInt(g);
                        s.addMark(mark);
                        output.append("Added grade for " + s.name + ": " + s.marks + "\n");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,"Invalid input");
                    }
                } else if (ch == 1) {
                    if (s.marks.size() == 0) {
                        JOptionPane.showMessageDialog(this,"No grades to update");
                        return;
                    }
                    String pos = JOptionPane.showInputDialog(this,"Enter grade number (1 - " + s.marks.size() + "):");
                    try {
                        int idx = Integer.parseInt(pos) - 1;
                        String g2 = JOptionPane.showInputDialog(this,"Enter new grade:");
                        int mark2 = Integer.parseInt(g2);
                        s.changeMark(idx, mark2);
                        output.append("Updated grades for " + s.name + ": " + s.marks + "\n");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,"Invalid input");
                    }
                }
            }
        }
    }

    void report() {
        if (list.size() == 0) {
            JOptionPane.showMessageDialog(this,"No students yet");
            return;
        }
        String[] arr = list.stream().map(s -> s.name).toArray(String[]::new);
        String who = (String) JOptionPane.showInputDialog(this,"Pick student:","Report",JOptionPane.QUESTION_MESSAGE,null,arr,arr[0]);
        if (who == null) return;
        for (Student s : list) {
            if (s.name.equals(who)) {
                output.append("Report for " + s.name + "\n");
                output.append("Grades: " + s.marks + "\n");
                output.append("Avg: " + s.average() + " High: " + s.highest() + " Low: " + s.lowest() + "\n\n");
            }
        }
    }

    void summary() {
        if (list.size() == 0) {
            JOptionPane.showMessageDialog(this,"No students yet");
            return;
        }
        output.append("Summary:\n");
        for (Student s : list) {
            output.append(s.name + " -> Avg: " + s.average() + ", High: " + s.highest() + ", Low: " + s.lowest() + "\n");
        }
        output.append("\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeManagerGUI app = new StudentGradeManagerGUI();
            app.setVisible(true);
        });
    }
}
