package main;

import models.Student;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class MainActivity {

	private static ArrayList<Student> students = new ArrayList<>();

	public static void main(String[] args) {
		// Initialiser l'interface graphique
		initGraphic();
		chargerEtudiants();
	}

	// Méthode pour initialiser l'interface graphique
	public static void initGraphic() {
		// Création du cadre (frame)
		JFrame frame = new JFrame("Ajout élève");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null); // Centrer la fenêtre

		// Création du panel avec un fond orange
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 165, 0)); // Couleur orange clair
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants

		// Prénom
		JLabel prenomLabel = new JLabel("Prénom: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(prenomLabel, gbc);

		JTextField prenomTextField = new JTextField(15);
		gbc.gridx = 1;
		panel.add(prenomTextField, gbc);

		// Nom
		JLabel nomLabel = new JLabel("Nom: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(nomLabel, gbc);

		JTextField nomTextField = new JTextField(15);
		gbc.gridx = 1;
		panel.add(nomTextField, gbc);

		// Âge
		JLabel ageLabel = new JLabel("Âge: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(ageLabel, gbc);

		JTextField ageTextField = new JTextField(5);
		gbc.gridx = 1;
		panel.add(ageTextField, gbc);

		// Numéro
		JLabel numeroLabel = new JLabel("Numéro: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(numeroLabel, gbc);

		JTextField numeroTextField = new JTextField(15);
		gbc.gridx = 1;
		panel.add(numeroTextField, gbc);

		// Bouton d'ajout
		JButton submitBtn = new JButton("Ajouter");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(submitBtn, gbc);

		// Gestion de la soumission
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String prenom = prenomTextField.getText();
				String nom = nomTextField.getText();
				int age = Integer.parseInt(ageTextField.getText());
				String numero = numeroTextField.getText();

				// Création d'un objet Student
				Student student = new Student(nom, prenom, age, numero);

				// Ajouter à la liste des étudiants
				students.add(student);

				// Sérialiser la liste des étudiants
				sauvegarderEtudiants();

				// Réinitialisation des champs
				prenomTextField.setText("");
				nomTextField.setText("");
				ageTextField.setText("");
				numeroTextField.setText("");
			}
		});

		// Bouton d'affichage des étudiants
		JButton afficherBtn = new JButton("Afficher Étudiants");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		panel.add(afficherBtn, gbc);

		afficherBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				afficherEtudiants();
				chargerEtudiants();
			}
		});

		// Ajout du panel au frame
		frame.add(panel);
		frame.setVisible(true);
	}

	// Méthode pour sérialiser et sauvegarder les étudiants
	public static void sauvegarderEtudiants() {
		try (FileOutputStream fileOut = new FileOutputStream("students.ser");
			 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(students);
			System.out.println("Étudiants sauvegardés avec succès.");
		} catch (IOException e) {
			System.out.println("Une erreur est survenue lors de la sauvegarde des étudiants.");
			e.printStackTrace();
		}
	}

	// Méthode pour désérialiser et charger les étudiants depuis le fichier
	public static void chargerEtudiants() {
		try (FileInputStream fileIn = new FileInputStream("students.ser");
			 ObjectInputStream in = new ObjectInputStream(fileIn)) {
			students = (ArrayList<Student>) in.readObject();
			System.out.println("Étudiants chargés avec succès.");
		} catch (EOFException eof) {
			System.out.println("Aucun étudiant trouvé, démarrage avec une liste vide.");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Une erreur est survenue lors du chargement des étudiants.");
			e.printStackTrace();
		}
	}

	// Méthode pour afficher les étudiants
	public static void afficherEtudiants() {
		if (students.isEmpty()) {
			System.out.println("Aucun étudiant enregistré.");
		} else {
			for (Student student : students) {
				System.out.println(student);
			}
		}
	}
}
