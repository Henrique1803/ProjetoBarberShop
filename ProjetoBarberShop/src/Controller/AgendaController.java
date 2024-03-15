/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Helper.AgendaHelper;
import Model.Agendamento;
import Model.Cliente;
import Model.DAO.AgendamentoDAO;
import Model.DAO.ClienteDAO;
import Model.DAO.ServicoDAO;
import Model.Servico;
import View.Agenda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author henri
 */
public class AgendaController {
    
    private final Agenda view;
    private final AgendaHelper helper;

    public AgendaController(Agenda view) {
        this.view = view;
        this.helper = new AgendaHelper(view);
    }
    
    public void atualizaTabela(){
        //buscar lista no banco de dados
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        ArrayList<Agendamento> agendamentos = agendamentoDAO.selectAll();
        
        // Ordena a lista usando um Comparator
        Collections.sort(agendamentos, Comparator.comparing(Agendamento::getHoraFormatada).reversed());
        
        //exibir esta lista na view
        this.helper.preencherTabela(agendamentos);
    } 
    
    public void atualizaCliente(){
        //buscar clientes do Banco de dados
        ClienteDAO clienteDAO = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDAO.selectAll();
        
        //exibir clientes no Combobox cliente
        this.helper.preencherClientes(clientes);
    }
    
    public void atualizaServico(){
        //buscar clientes do Banco de dados
        ServicoDAO servicoDAO = new ServicoDAO();
        ArrayList<Servico> servicos = servicoDAO.selectAll();
        
        //exibir clientes no Combobox cliente
        this.helper.preencherServicos(servicos);
    }
    
    public void atualizaValor(){
        Servico servico = helper.obterServico();
        helper.setarValor(servico.getValor());
    }
    
    public void agendar(){
        
        if(helper.validarCampos()){
            //Buscar Objeto Agendamento da Tela
            Agendamento agendamento = (Agendamento) helper.obterModelo();
            //Salva Objeto no banco de dados
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            agendamentoDAO.insert(agendamento);
            //inserir elemento na tabela
            atualizaTabela();
            helper.limparTela();
        }
    }
    
    public int editar(){
        JTable tabela = view.getTabelaAgendamentos();
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada != -1) {
            int id = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            helper.preencherCampos(agendamentoDAO.buscar(id));
            
            view.getBtnAgendar().setVisible(false);
            view.getBtnSalvar().setVisible(true);
            
            return id;
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma linha selecionada.");
            return 0;
        }
    }
    
    public void salvar(int id){
        if(helper.validarCampos()){
            //Buscar Objeto Agendamento da Tela
            Agendamento agendamento = (Agendamento) helper.obterModelo();
            agendamento.setId(id);
            //Atualiza o Objeto no banco de dados
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            System.out.println(agendamentoDAO.update(agendamento));

            atualizaTabela();
            helper.limparTela();

            view.getBtnAgendar().setVisible(true);
            view.getBtnSalvar().setVisible(false);
        }
    }
    
    public void excluir(){
        JTable tabela = view.getTabelaAgendamentos();
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada != -1) {
            int id = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            agendamentoDAO.delete(agendamentoDAO.buscar(id));
            
            atualizaTabela();
            helper.limparTela();
            view.getBtnAgendar().setVisible(true);
            view.getBtnSalvar().setVisible(false);
        
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma linha selecionada.");
        }
    }
    
}
