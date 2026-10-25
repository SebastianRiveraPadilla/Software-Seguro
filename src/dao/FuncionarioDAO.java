package dao;

import connection.ConnectionDB;
import model.Funcionario;
import model.GrupoFamiliar;
import model.InfoAcademica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public List<Funcionario> listar() throws DAOException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios ORDER BY apellidos, nombres";
        
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                lista.add(mapearFuncionario(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar funcionarios: " + e.getMessage(), e);
        }
        return lista;
    }

    public void insertar(Funcionario f) throws DAOException {
        String sql = "INSERT INTO funcionarios (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) VALUES (?,?,?,?,?,?,?,?,?)";
        
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            ps.setString(1, f.getTipoIdentificacion());
            ps.setString(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            ps.setString(5, f.getEstadoCivil());
            ps.setString(6, f.getSexo());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            
            if (f.getFechaNacimiento() != null) {
                ps.setDate(9, f.getFechaNacimiento());
            } else {
                ps.setNull(9, Types.DATE);
            }
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    f.setIdFuncionario(rs.getInt(1));
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DAOException("El número de identificación ya está registrado.", e);
        } catch (SQLException e) {
            throw new DAOException("Error al insertar funcionario: " + e.getMessage(), e);
        }
    }

    public void actualizar(Funcionario f) throws DAOException {
        String sql = "UPDATE funcionarios SET tipo_identificacion=?, numero_identificacion=?, nombres=?, apellidos=?, estado_civil=?, sexo=?, direccion=?, telefono=?, fecha_nacimiento=? WHERE id_funcionario=?";
        
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, f.getTipoIdentificacion());
            ps.setString(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            ps.setString(5, f.getEstadoCivil());
            ps.setString(6, f.getSexo());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            
            if (f.getFechaNacimiento() != null) {
                ps.setDate(9, f.getFechaNacimiento());
            } else {
                ps.setNull(9, Types.DATE);
            }
            ps.setInt(10, f.getIdFuncionario());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar funcionario: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM funcionarios WHERE id_funcionario = ?";
        
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar funcionario: " + e.getMessage(), e);
        }
    }

    public Funcionario buscarPorId(int id) throws DAOException {
        String sql = "SELECT * FROM funcionarios WHERE id_funcionario = ?";
        
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearFuncionario(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar funcionario: " + e.getMessage(), e);
        }
        return null;
    }

    public List<GrupoFamiliar> listarGrupoFamiliar(int idFuncionario) throws DAOException {
        List<GrupoFamiliar> lista = new ArrayList<>();
        String sql = "SELECT * FROM grupo_familiar WHERE id_funcionario = ?";
        
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, idFuncionario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GrupoFamiliar g = new GrupoFamiliar();
                    g.setIdGrupoFamiliar(rs.getInt("id_grupo_familiar"));
                    g.setIdFuncionario(rs.getInt("id_funcionario"));
                    g.setNombres(rs.getString("nombres"));
                    g.setApellidos(rs.getString("apellidos"));
                    g.setRol(rs.getString("rol"));
                    g.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    lista.add(g);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar familiares: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<InfoAcademica> listarInfoAcademica(int idFuncionario) throws DAOException {
        List<InfoAcademica> lista = new ArrayList<>();
        String sql = "SELECT * FROM info_academica WHERE id_funcionario = ?";
        
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, idFuncionario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InfoAcademica a = new InfoAcademica();
                    a.setIdAcademica(rs.getInt("id_academica"));
                    a.setIdFuncionario(rs.getInt("id_funcionario"));
                    a.setUniversidad(rs.getString("universidad"));
                    a.setNivelEstudio(rs.getString("nivel_estudio"));
                    a.setTituloEstudio(rs.getString("titulo_estudio"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar info académica: " + e.getMessage(), e);
        }
        return lista;
    }

    private Funcionario mapearFuncionario(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getInt("id_funcionario"));
        f.setTipoIdentificacion(rs.getString("tipo_identificacion"));
        f.setNumeroIdentificacion(rs.getString("numero_identificacion"));
        f.setNombres(rs.getString("nombres"));
        f.setApellidos(rs.getString("apellidos"));
        f.setEstadoCivil(rs.getString("estado_civil"));
        f.setSexo(rs.getString("sexo"));
        f.setDireccion(rs.getString("direccion"));
        f.setTelefono(rs.getString("telefono"));
        f.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        return f;
    }
}