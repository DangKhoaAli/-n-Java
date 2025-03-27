
import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        super("Chương trình quản lý thư viện");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,800);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Quản lý sách", new BookPanel());

        tabbedPane.addTab("Quản lý độc giả", new ReaderPanel());

        tabbedPane.addTab("Quản lý thủ thư", new LibrarianPanel());

        tabbedPane.addTab("Quản lý phiếu mượn", new LoanPanel());

        tabbedPane.addTab("Quản lý phiếu trả", new PayPanel());
        
        getContentPane().add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
