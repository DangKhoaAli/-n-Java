package gui;

import java.awt.*;
import javax.swing.*;

public class LoginPanel extends JDialog {
    private JTextField userField = new JTextField(20);
    private JPasswordField passField = new JPasswordField(20);
    private boolean succeeded = false;
    private String userRole = null;

    public LoginPanel(Frame parent) {
        super(parent, "Đăng nhập", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        // Buttons
        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");
        JPanel btnPanel = new JPanel();
        btnPanel.add(loginBtn);
        btnPanel.add(cancelBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            String role = authenticate(user, pass);  // authenticate returns role or null
            if (role != null) {
                succeeded = true;
                userRole = role;
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginPanel.this,
                        "Sai tên hoặc mật khẩu", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                userField.setText("");
                passField.setText("");
                succeeded = false;
            }
        });

        cancelBtn.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.PAGE_END);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    /**
     * Trả về role nếu đúng, ngược lại null
     */
    private String authenticate(String user, String pass) {
        if ("admin".equals(user) && "password".equals(pass)) {
            return "admin";
        }
        if ("staff".equals(user) && "1234".equals(pass)) {
            return "staff";
        }
        return null;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getUserRole() {
        return userRole;
    }
}
