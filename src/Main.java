import view.FuncionarioForm;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el estilo del sistema.");
        }

        SwingUtilities.invokeLater(() -> {
            FuncionarioForm ventana = new FuncionarioForm();
            ventana.setVisible(true);
        });
    }
}