package view;

import dao.DAOException;
import dao.FuncionarioDAO;
import model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class FuncionarioForm extends JFrame {

    private final FuncionarioDAO dao = new FuncionarioDAO();

    private JTable tablaFuncionarios;
    private DefaultTableModel modeloFuncionarios;
    private JLabel lblAvisoGrupo, lblAvisoAcademica;
    private DefaultTableModel modeloGrupo;
    private DefaultTableModel modeloAcademica;

    private JComboBox<String> cmbTipoId, cmbEstadoCivil, cmbSexo;
    private JTextField txtNumId, txtNombres, txtApellidos, txtDireccion, txtTelefono, txtFechaNac, txtId;

    private JButton btnNuevo, btnGuardar, btnActualizar, btnEliminar, btnLimpiar;

    private int idSeleccionado = -1;

    public FuncionarioForm() {
        setTitle("Gestión de Funcionarios - RRHH");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(crearPanelFormulario(), BorderLayout.NORTH);
        add(crearPanelTablas(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);

        cargarFuncionarios();
        limpiarFormulario();
    }

    private JPanel crearPanelFormulario() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Datos del Funcionario"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(5); 
        txtId.setEditable(false);
        cmbTipoId = new JComboBox<>(new String[]{"CC", "CE", "TI", "PA"});
        txtNumId = new JTextField(15);
        txtNombres = new JTextField(15);
        txtApellidos = new JTextField(15);
        cmbEstadoCivil = new JComboBox<>(new String[]{"Soltero/a", "Casado/a", "Unión libre", "Separado/a", "Viudo/a"});
        cmbSexo = new JComboBox<>(new String[]{"M", "F", "Otro"});
        txtDireccion = new JTextField(20);
        txtTelefono = new JTextField(15);
        txtFechaNac = new JTextField(12);
        txtFechaNac.setToolTipText("Formato: yyyy-MM-dd (Ej: 1990-05-20)");

        g.gridx = 0; g.gridy = 0; p.add(new JLabel("ID:"), g);
        g.gridx = 1; p.add(txtId, g);
        g.gridx = 2; p.add(new JLabel("Tipo ID:"), g);
        g.gridx = 3; p.add(cmbTipoId, g);
        g.gridx = 4; p.add(new JLabel("Número ID:"), g);
        g.gridx = 5; p.add(txtNumId, g);

        g.gridx = 0; g.gridy = 1; p.add(new JLabel("Nombres:"), g);
        g.gridx = 1; p.add(txtNombres, g);
        g.gridx = 2; p.add(new JLabel("Apellidos:"), g);
        g.gridx = 3; p.add(txtApellidos, g);
        g.gridx = 4; p.add(new JLabel("Estado Civil:"), g);
        g.gridx = 5; p.add(cmbEstadoCivil, g);

        g.gridx = 0; g.gridy = 2; p.add(new JLabel("Sexo:"), g);
        g.gridx = 1; p.add(cmbSexo, g);
        g.gridx = 2; p.add(new JLabel("Fecha Nac:"), g);
        g.gridx = 3; p.add(txtFechaNac, g);
        g.gridx = 4; p.add(new JLabel("Teléfono:"), g);
        g.gridx = 5; p.add(txtTelefono, g);

        g.gridx = 0; g.gridy = 3; p.add(new JLabel("Dirección:"), g);
        g.gridx = 1; g.gridwidth = 5; p.add(txtDireccion, g);

        return p;
    }

    private JTabbedPane crearPanelTablas() {
        JTabbedPane tabs = new JTabbedPane();

        modeloFuncionarios = new DefaultTableModel(new Object[]{
                "ID", "Tipo", "Número", "Nombres", "Apellidos", "Estado", "Sexo", "Teléfono", "Fecha Nac"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaFuncionarios = new JTable(modeloFuncionarios);
        tablaFuncionarios.setRowHeight(25);
        tablaFuncionarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaFuncionarios.getSelectedRow() >= 0) {
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                int fila = tablaFuncionarios.getSelectedRow();
                int id = (int) modeloFuncionarios.getValueAt(fila, 0);
                cargarFuncionarioEnFormulario(id);
            }
        });
        tabs.addTab("Funcionarios", new JScrollPane(tablaFuncionarios));

        modeloGrupo = new DefaultTableModel(new Object[]{"Nombres", "Apellidos", "Rol", "Fecha Nac"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JPanel panelGrupo = new JPanel(new BorderLayout());
        lblAvisoGrupo = new JLabel("Seleccione un funcionario en la pestaña 'Funcionarios' para ver sus datos.", SwingConstants.CENTER);
        lblAvisoGrupo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblAvisoGrupo.setForeground(Color.GRAY);
        lblAvisoGrupo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelGrupo.add(lblAvisoGrupo, BorderLayout.NORTH);
        panelGrupo.add(new JScrollPane(new JTable(modeloGrupo)), BorderLayout.CENTER);
        tabs.addTab("Grupo Familiar (Lectura)", panelGrupo);

        modeloAcademica = new DefaultTableModel(new Object[]{"Universidad", "Nivel", "Título"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JPanel panelAcademica = new JPanel(new BorderLayout());
        lblAvisoAcademica = new JLabel("Seleccione un funcionario en la pestaña 'Funcionarios' para ver sus datos.", SwingConstants.CENTER);
        lblAvisoAcademica.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblAvisoAcademica.setForeground(Color.GRAY);
        lblAvisoAcademica.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelAcademica.add(lblAvisoAcademica, BorderLayout.NORTH);
        panelAcademica.add(new JScrollPane(new JTable(modeloAcademica)), BorderLayout.CENTER);
        tabs.addTab("Info Académica (Lectura)", panelAcademica);

        return tabs;
    }

    private JPanel crearPanelBotones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnNuevo = new JButton("Nuevo / Limpiar");
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        p.add(btnNuevo); p.add(btnGuardar); p.add(btnActualizar); p.add(btnEliminar);

        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> accionGuardar());
        btnActualizar.addActionListener(e -> accionActualizar());
        btnEliminar.addActionListener(e -> accionEliminar());

        return p;
    }

    private void cargarFuncionarios() {
        try {
            modeloFuncionarios.setRowCount(0);
            List<Funcionario> lista = dao.listar();
            for (Funcionario f : lista) {
                modeloFuncionarios.addRow(new Object[]{
                        f.getIdFuncionario(), f.getTipoIdentificacion(), f.getNumeroIdentificacion(),
                        f.getNombres(), f.getApellidos(), f.getEstadoCivil(), f.getSexo(), f.getTelefono(), f.getFechaNacimiento()
                });
            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarFuncionarioEnFormulario(int id) {
        try {
            Funcionario f = dao.buscarPorId(id);
            if (f == null) return;
            
            idSeleccionado = f.getIdFuncionario();
            txtId.setText(String.valueOf(f.getIdFuncionario()));
            cmbTipoId.setSelectedItem(f.getTipoIdentificacion());
            txtNumId.setText(f.getNumeroIdentificacion());
            txtNombres.setText(f.getNombres());
            txtApellidos.setText(f.getApellidos());
            cmbEstadoCivil.setSelectedItem(f.getEstadoCivil());
            cmbSexo.setSelectedItem(f.getSexo());
            txtDireccion.setText(f.getDireccion());
            txtTelefono.setText(f.getTelefono());
            txtFechaNac.setText(f.getFechaNacimiento() != null ? f.getFechaNacimiento().toString() : "");

            modeloGrupo.setRowCount(0);
            dao.listarGrupoFamiliar(id).forEach(g -> 
                modeloGrupo.addRow(new Object[]{g.getNombres(), g.getApellidos(), g.getRol(), g.getFechaNacimiento()})
            );
            
            if (modeloGrupo.getRowCount() > 0) {
                lblAvisoGrupo.setVisible(false);
            } else {
                lblAvisoGrupo.setText("Este funcionario no tiene familiares registrados.");
                lblAvisoGrupo.setVisible(true);
            }

            modeloAcademica.setRowCount(0);
            dao.listarInfoAcademica(id).forEach(a -> 
                modeloAcademica.addRow(new Object[]{a.getUniversidad(), a.getNivelEstudio(), a.getTituloEstudio()})
            );
            
            if (modeloAcademica.getRowCount() > 0) {
                lblAvisoAcademica.setVisible(false);
            } else {
                lblAvisoAcademica.setText("Este funcionario no tiene información académica registrada.");
                lblAvisoAcademica.setVisible(true);
            }

        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionGuardar() {
        try {
            Funcionario f = leerFormulario();
            dao.insertar(f);
            JOptionPane.showMessageDialog(this, "Funcionario guardado con éxito.");
            limpiarFormulario();
            cargarFuncionarios();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizar() {
        if (idSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un funcionario de la tabla primero.");
            return;
        }
        try {
            Funcionario f = leerFormulario();
            f.setIdFuncionario(idSeleccionado);
            dao.actualizar(f);
            JOptionPane.showMessageDialog(this, "Funcionario actualizado con éxito.");
            cargarFuncionarios();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        if (idSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un funcionario de la tabla primero.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar este funcionario?\nSe borrarán también sus familiares y estudios.", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(idSeleccionado);
                JOptionPane.showMessageDialog(this, "Funcionario eliminado.");
                limpiarFormulario();
                cargarFuncionarios();
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Funcionario leerFormulario() {
        if (txtNumId.getText().trim().isEmpty() || txtNombres.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de identificación y los nombres son obligatorios.");
        }

        Funcionario f = new Funcionario();
        f.setTipoIdentificacion((String) cmbTipoId.getSelectedItem());
        f.setNumeroIdentificacion(txtNumId.getText().trim());
        f.setNombres(txtNombres.getText().trim());
        f.setApellidos(txtApellidos.getText().trim());
        f.setEstadoCivil((String) cmbEstadoCivil.getSelectedItem());
        f.setSexo((String) cmbSexo.getSelectedItem());
        f.setDireccion(txtDireccion.getText().trim());
        f.setTelefono(txtTelefono.getText().trim());

        if (!txtFechaNac.getText().trim().isEmpty()) {
            try {
                f.setFechaNacimiento(Date.valueOf(txtFechaNac.getText().trim()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Formato de fecha incorrecto. Usa yyyy-MM-dd");
            }
        }
        return f;
    }

    private void limpiarFormulario() {
        idSeleccionado = -1;
        txtId.setText("");
        cmbTipoId.setSelectedIndex(0);
        txtNumId.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        cmbEstadoCivil.setSelectedIndex(0);
        cmbSexo.setSelectedIndex(0);
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtFechaNac.setText("");
        tablaFuncionarios.clearSelection();

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        modeloGrupo.setRowCount(0);
        lblAvisoGrupo.setText("Seleccione un funcionario en la pestaña 'Funcionarios' para ver sus datos.");
        lblAvisoGrupo.setVisible(true);
        
        modeloAcademica.setRowCount(0);
        lblAvisoAcademica.setText("Seleccione un funcionario en la pestaña 'Funcionarios' para ver sus datos.");
        lblAvisoAcademica.setVisible(true);
        
        tablaFuncionarios.clearSelection();
    }

}