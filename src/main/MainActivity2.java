package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

import models.Student;

public class MainActivity2 {

    private static ArrayList<Student> students = new ArrayList<>();
    private static JFrame frame;

    public static void main(String[] args) {
        loadStudents();
        initGraphic();
    }

    private static void loadStudents() {
        File file = new File("student.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    try {
                        Student deserializedStudent = (Student) ois.readObject();
                        students.add(deserializedStudent);
                    } catch (EOFException eof) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initGraphic() {
        frame = new JFrame("Ajout et affichage des étudiants");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBackground(new Color(255, 165, 0)); // Jaune orange

        Font font = new Font("Times New Roman", Font.PLAIN, 16);
        Color textFieldColor = Color.BLACK;
        Color textColor = Color.WHITE;

        // Création et configuration des champs de texte
        JTextField prenomTextField = new JTextField(15);
        prenomTextField.setFont(font);
        prenomTextField.setBackground(textFieldColor);
        prenomTextField.setForeground(textColor);
        prenomTextField.setHorizontalAlignment(JTextField.CENTER);

        JTextField nomTextField = new JTextField(15);
        nomTextField.setFont(font);
        nomTextField.setBackground(textFieldColor);
        nomTextField.setForeground(textColor);
        nomTextField.setHorizontalAlignment(JTextField.CENTER);

        JTextField ageTextField = new JTextField(5);
        ageTextField.setFont(font);
        ageTextField.setBackground(textFieldColor);
        ageTextField.setForeground(textColor);
        ageTextField.setHorizontalAlignment(JTextField.CENTER);

        JTextField numeroTextField = new JTextField(15);
        numeroTextField.setFont(font);
        numeroTextField.setBackground(textFieldColor);
        numeroTextField.setForeground(textColor);
        numeroTextField.setHorizontalAlignment(JTextField.CENTER);

        // Création et configuration des labels
        JLabel prenomLabel = new JLabel("Prénom:");
        prenomLabel.setFont(font);
        prenomLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nomLabel = new JLabel("Nom:");
        nomLabel.setFont(font);
        nomLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel ageLabel = new JLabel("Âge:");
        ageLabel.setFont(font);
        ageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel numeroLabel = new JLabel("Numéro:");
        numeroLabel.setFont(font);
        numeroLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(prenomLabel);
        panel.add(prenomTextField);
        panel.add(nomLabel);
        panel.add(nomTextField);
        panel.add(ageLabel);
        panel.add(ageTextField);
        panel.add(numeroLabel);
        panel.add(numeroTextField);

        JButton submitBtn = new JButton("Ajouter");
        JButton showBtn = new JButton("Afficher les étudiants");

        submitBtn.setFont(font);
        showBtn.setFont(font);

        panel.add(submitBtn);
        panel.add(showBtn);

        submitBtn.addActionListener(e -> handleAddStudent(prenomTextField, nomTextField, ageTextField, numeroTextField));
        showBtn.addActionListener(e -> handleShowStudents());

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void handleAddStudent(JTextField prenomTextField, JTextField nomTextField, JTextField ageTextField, JTextField numeroTextField) {
        String prenom = prenomTextField.getText();
        String nom = nomTextField.getText();
        String ageText = ageTextField.getText();
        String numero = numeroTextField.getText();

        if (prenom.isEmpty() || nom.isEmpty() || ageText.isEmpty() || numero.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Tous les champs doivent être remplis", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            Student student = new Student(nom, prenom, age, numero);
            students.add(student);
            saveStudents();
            JOptionPane.showMessageDialog(frame, "Étudiant ajouté avec succès");

            // Effacer les champs après l'ajout.
            prenomTextField.setText("");
            nomTextField.setText("");
            ageTextField.setText("");
            numeroTextField.setText("");

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(frame, "L'âge doit être un nombre valide", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ie) {
            JOptionPane.showMessageDialog(frame, "Une erreur est survenue lors de la création du fichier student.dat", "Erreur", JOptionPane.ERROR_MESSAGE);
            ie.printStackTrace();
        }
    }

    private static void saveStudents() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.dat"))) {
            for (Student student : students) {
                oos.writeObject(student);
            }
        }
    }

    private static void handleShowStudents() {
        StringBuilder sb = new StringBuilder();
        if(students.isEmpty()) {
            sb.append("Aucun étudiant n'a été ajouté.");
        }else {
        for (Student student : students) {
            sb.append(student.toString()).append("\n");
        }
    }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Liste des étudiants", JOptionPane.INFORMATION_MESSAGE);
    }
}
