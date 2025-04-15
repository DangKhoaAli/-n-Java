package gui;

import javax.swing.*;

public class Main extends JFrame {
    public Main(String userRole) {
        super("Chương trình quản lý thư viện");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,800);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        BookPanel bookPanel = new BookPanel();
        tabbedPane.addTab("Quản lý sách", new BookPanel());

        tabbedPane.addTab("Quản lý độc giả", new ReaderPanel());

        if("admin".equalsIgnoreCase(userRole)) {
            tabbedPane.addTab("Quản lý thủ thư", new LibrarianPanel());
        }

        tabbedPane.addTab("Quản lý phiếu mượn", new LoanPanel(bookPanel));

        tabbedPane.addTab("Quản lý phiếu trả", new PayPanel());
        
        getContentPane().add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPanel login = new LoginPanel(null);
            login.setVisible(true);

            if(login.isSucceeded()) {
                String role = login.getUserRole();
                new Main(role).setVisible(true);
            } else {
                System.exit(0);
            }

        });
    }
}
