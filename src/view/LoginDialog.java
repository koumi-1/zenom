package view;

import javax.swing.*;

public class LoginDialog extends JDialog {
    private boolean authenticated = false;

    public LoginDialog(JFrame parent) {
        super(parent, "Login", true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if ("admin".equals(username) && "password".equals(password)) { // Simple placeholder logic
                authenticated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(loginButton);
        add(panel);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
