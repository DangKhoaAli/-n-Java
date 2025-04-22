package gui;

import javax.swing.*;

public class Main extends JFrame {
    public Main(String userRole) {
        super("Chương trình quản lý thư viện");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,800);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Quản lý sách", new BookPanel());

        tabbedPane.addTab("Quản lý độc giả", new ReaderPanel());

        if("Admin".equalsIgnoreCase(userRole)) {
            tabbedPane.addTab("Quản lý thủ thư", new LibrarianPanel());
        }

        tabbedPane.addTab("Quản lý phiếu mượn", new LoanPanel(userRole));

        tabbedPane.addTab("Quản lý phiếu trả", new PayPanel(userRole));

        if("Admin".equalsIgnoreCase(userRole)) {
            tabbedPane.addTab("Thống kê", new ReportPanel());
        }
        
        getContentPane().add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPanel login = new LoginPanel();
            login.setVisible(true);
    
            login.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    if (login.isSucceeded()) {
                        String role = login.getUserRole();
                        new Main(role).setVisible(true);
                    } else {
                        System.exit(0);
                    }
                }
            });
        });
    }
    
}
