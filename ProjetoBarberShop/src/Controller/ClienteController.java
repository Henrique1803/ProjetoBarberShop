/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Helper.ClienteHelper;
import Model.Cliente;
import Model.DAO.ClienteDAO;
import View.ClienteView;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author henri
 */
public class ClienteController {
    
    private final ClienteView view;
    private final ClienteHelper helper;

    public ClienteController(ClienteView view) {
        this.view = view;
        this.helper = new ClienteHelper(view);
    }
    
    public void atualizaTabela(){
        //buscar lista no banco de dados
        ClienteDAO clienteDAO = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDAO.selectAll();
        
        //exibir esta lista na view
        this.helper.preencherTabela(clientes);
    } 
    
    public void cadastrar(){
        
        if(helper.validarCampos()){
      
            Cliente cliente = (Cliente) helper.obterModelo();
            //Salva Objeto no banco de dados
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.insert(cliente);
            //inserir elemento na tabela
            atualizaTabela();
            helper.limparTela();
        
        }
    }
    
    public int editar(){
        JTable tabela = view.getTabelaClientes();
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada != -1) {
            int id = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            ClienteDAO clienteDAO = new ClienteDAO();
            helper.preencherCampos(clienteDAO.buscar(id));
            
            view.getBtnCadastrar().setVisible(false);
            view.getBtnSalvar().setVisible(true);
            
            return id;
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma linha selecionada.");
            return 0;
        }
    }
    
    public void salvar(int id){
        if(helper.validarCampos()){
            //Buscar Objeto Cliente da Tela
            Cliente cliente = (Cliente) helper.obterModelo();
            cliente.setId(id);
            //Atualiza o Objeto no banco de dados
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.update(cliente);

            atualizaTabela();
            helper.limparTela();
            
            view.getBtnCadastrar().setVisible(true);
            view.getBtnSalvar().setVisible(false);
        }
    }
    
    public void excluir(){
        JTable tabela = view.getTabelaClientes();
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada != -1) {
            int id = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.delete(clienteDAO.buscar(id));
            
            atualizaTabela();
            helper.limparTela();
            view.getBtnCadastrar().setVisible(true);
            view.getBtnSalvar().setVisible(false);
        
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma linha selecionada.");
        }
    }
    
}
