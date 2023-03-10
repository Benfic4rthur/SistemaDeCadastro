package MinhaTableModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import pacoteDados.Cliente;

public class MinhaTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private List<Cliente> usuarios;
    private String[] colunas = {"ID", "Nome", "E-mail", "Telefone", "Data de Nascimento", "Profissão", "Documento", "Tipo de Pessoa", "Endereço"};
    private Object[][] dados;
	private String[] columnNames;

    public MinhaTableModel(List<Cliente> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = usuarios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cliente.getId();
            case 1:
                return cliente.getNome();
            case 2:
                return cliente.getEmail();
            case 3:
                return cliente.getTelefone();
            case 4:
                return cliente.getDatanascimento();
            case 5:
                return cliente.getProfissao();
            case 6:
                return cliente.getDocumento();
            case 7:
                return cliente.getTipopessoa();
            case 8:
                return cliente.getEndereco();
            default:
                return null;
        }
    }

    public void setDados(Object[][] dados) {
        this.dados = dados;
        fireTableDataChanged();
    }
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
        fireTableStructureChanged();
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void atualizar(List<Cliente> usuarios) {
        this.usuarios = usuarios;
        fireTableDataChanged();
    }

    public void setRowCount(int rowCount) {
        Object[][] newDados = new Object[rowCount][colunas.length];
        for (int i = 0; i < rowCount; i++) {
            if (i < dados.length) {
                System.arraycopy(dados[i], 0, newDados[i], 0, colunas.length);
            } else {
                for (int j = 0; j < colunas.length; j++) {
                    newDados[i][j] = null;
                }
            }
        }
        setDados(newDados);
    }

    public void addRow(Cliente cliente) {
        Object[] novaLinha = {cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone(),
                              cliente.getDatanascimento(), cliente.getProfissao(), cliente.getDocumento(),
                              cliente.getTipopessoa(), cliente.getEndereco()};
        dados = Arrays.copyOf(dados, getRowCount() + 1);
        dados[getRowCount() - 1] = novaLinha;
        fireTableDataChanged();
    }
}