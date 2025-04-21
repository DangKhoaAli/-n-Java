package gui;

import DAO.Staff_DAO;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

public class LoginPanel extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean succeeded = false;
    private String userRole = null;

    public LoginPanel() {
        setTitle("Login");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        ImageIcon bgIcon = new ImageIcon("D:\\Java\\src\\img\\login.jpg");

        // ImageIcon bgIcon = new ImageIcon("C:\\Users\\Admin\\OneDrive\\Desktop\\Project-Java\\-n-Java\\src\\img\\login.png");

        Image scale = bgIcon.getImage().getScaledInstance(850, 600, Image.SCALE_SMOOTH);
        ImageIcon imgLabel = new ImageIcon(scale);
        JLabel background = new JLabel(imgLabel);
        background.setBounds(0, 0, 850, 600);
        background.setLayout(null);

        JPanel formPanel = new RoundedPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(275, 190, 285, 140);
        formPanel.setBackground(new Color(120, 170, 200, 255));
        background.add(formPanel);

        usernameField = new RoundedTextField("Username");
        usernameField.setForeground(Color.GRAY);
        usernameField.setBounds(50, 20, 200, 30);
        formPanel.add(usernameField);
        usernameField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("Username");
                }
            }
        });

        passwordField = new RoundedPasswordField("Password");
        passwordField.setEchoChar((char) 0);
        passwordField.setForeground(Color.GRAY);
        passwordField.setBounds(50, 65, 200, 30);
        formPanel.add(passwordField);

        
        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('●');
                    passwordField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Password");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        JButton loginButton = new RoundedButtonLogin("Login");
        loginButton.setBounds(50, 110, 90, 30);
        formPanel.add(loginButton);

        JButton cancelButton = new RoundedButtonCancel("Cancel");
        cancelButton.setBounds(160, 110, 90, 30);
        formPanel.add(cancelButton);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());
            String role = authenticate(user, pass);
            if (role != null) {
                succeeded = true;
                userRole = role;
                dispose(); // Tắt login window
            } else {
                JOptionPane.showMessageDialog(LoginPanel.this,
                        "Sai tên hoặc mật khẩu", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
                succeeded = false;
            }
        });

        cancelButton.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        setContentPane(background);
        setVisible(true);
    }

    private String authenticate(String user, String pass) {
    try {
        // Kiểm tra nếu là admin
        if ("admin".equals(user) && "1234".equals(pass)) {
            return "admin";
        }

        Staff_DAO staffDao = new Staff_DAO();
        String[] account = staffDao.generateAccountByStaffID(user);
        if (account != null) {
            String usernameFromDb = account[0]; 
            String passwordFromDb = account[1]; 

            if (user.equals(usernameFromDb) && pass.equals(passwordFromDb)) {
                return user;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
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

class RoundedPanel extends JPanel {
    private int arc = 20;

    public RoundedPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        super.paintComponent(g);
        g2.dispose();
    }
}

class RoundedTextField extends JTextField {
    private int arc = 15;

    public RoundedTextField(String text) {
        super(text);
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
        g2.dispose();
    }
}

class RoundedPasswordField extends JPasswordField {
    private int arc = 15;
    private boolean showPass = false;
    private Icon eyeIcon;
    private Icon eyeSlashIcon;
    private Rectangle iconBounds;

    public RoundedPasswordField(String text) {
        super(text);
        setOpaque(false);
        setText(text);
        setForeground(Color.GRAY);
        setEchoChar((char) 0); // Mặc định hiển thị placeholder
        setMargin(new Insets(2, 5, 2, 25)); // Dành khoảng trống cho icon
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        // Load icon
        eyeIcon = resizeIcon(new ImageIcon(getClass().getResource("/img/eye.png")), 16, 16);
        eyeSlashIcon = resizeIcon(new ImageIcon(getClass().getResource("/img/eye-slash.png")), 16, 16); // sửa từ .svg thành .png

        // Toggle hiển thị mật khẩu khi nhấn vào icon
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (iconBounds != null && iconBounds.contains(e.getPoint())) {
                    showPass = !showPass;
                    if (showPass) {
                        setEchoChar((char) 0); // hiện mật khẩu
                    } else {
                        setEchoChar('●'); // ẩn mật khẩu
                    }
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (iconBounds != null && iconBounds.contains(e.getPoint())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
        
    }

    private Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        // Vẽ icon bên phải
        Icon icon = showPass ? eyeSlashIcon : eyeIcon;
        int iconX = getWidth() - icon.getIconWidth() - 8;
        int iconY = (getHeight() - icon.getIconHeight()) / 2;
        icon.paintIcon(this, g2, iconX, iconY);

        iconBounds = new Rectangle(iconX, iconY, icon.getIconWidth(), icon.getIconHeight());

        g2.dispose();
        super.paintComponent(g); // phải gọi sau khi vẽ icon để tránh che mất
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.dispose();
    }
}


class RoundedButtonLogin extends JButton {
    private int arc = 20;

    public RoundedButtonLogin(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE); 
        setBackground(new Color(250, 140, 55));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        super.paintComponent(g);
        g2.dispose();
    }
}

class RoundedButtonCancel extends JButton {
    private int arc = 20;

    public RoundedButtonCancel(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE); 
        setBackground(new Color(80, 100, 140)); 
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        super.paintComponent(g);
        g2.dispose();
    }
}