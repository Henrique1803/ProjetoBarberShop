/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;

import View.ClienteView;
import Model.Cliente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author henri
 */
public class ClienteHelper implements IHelper{
     private final ClienteView view;

    public ClienteHelper(ClienteView view) {
        this.view = view;
    }
    
    public boolean validarCampos(){
        String nome = view.getTextNome().getText();
        
        String rg = view.getTextRg().getText();
        
        String telefone = view.getTextTelefone().getText();
        
        String data = view.getTextData().getText();
        
        String sexoCompleto = view.getjComboBoxSexo().getSelectedItem().toString(); 
        
        String email = view.getTextEmail().getText();
        
        String cep = view.getTextCep().getText();
        
        String endereco = view.getTextEndereco().getText();
        
        if(nome.equals("") || rg.equals("") || telefone.equals("(  )         ") || data.equals("  /  /    ") || email.equals("") || cep.equals("") || endereco.equals("")){
            view.exibeMensagem("Preencha todos os campos!");
            return false;
        }else if(rg.length() != 10){
            view.exibeMensagem("O RG está incompleto!");
            return false;
        }else if(cep.length() != 8){
            view.exibeMensagem("O CEP está incompleto!");
            return false;
        }else if(telefone.length() != 13){
            view.exibeMensagem("O telefone está incompleto!");
            return false;
        }else if(data.length() != 10){
            view.exibeMensagem("A data de nascimento está incompleta!");
            return false;
        }else{
            // Obter a data atual
            Date dataAtual = new Date();
            Date dataDigitada = new Date();

            // Formato da data
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try{
                dataDigitada = formato.parse(data);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. Certifique-se de usar o formato dd/MM/yyyy.");
            }

            if(dataDigitada.after(dataAtual)){
                view.exibeMensagem("A data de nascimento é maior que a data atual!");
                return false;       
            }
        
            return true;
        }
    }
    
    public void preencherTabela(ArrayList<Cliente> clientes){
        DefaultTableModel tableModel = (DefaultTableModel) view.getTabelaClientes().getModel();
        tableModel.setNumRows(0);
        
        //percorrer a lista preenchendo o table model
        for (Cliente cliente : clientes) {
            
            tableModel.addRow(new Object[]{
                cliente.getId(),
                cliente.getNome(),
                cliente.getRg(),
                cliente.getTelefone(),
                cliente.getDataFormatada(),
                cliente.getSexo(),
                cliente.getEmail(),
                cliente.getCep(),
                cliente.getEndereco()
            });
            
        }
    }

    /*public void preencherClientes(ArrayList<Cliente> clientes) {
        DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) view.getjComboBoxCliente().getModel();
        
        for (Cliente cliente : clientes) {
            comboBoxModel.addElement(cliente);
        }
    }*/

    /*public void preencherServicos(ArrayList<Servico> servicos) {
        DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) view.getjComboBoxServico().getModel();
        
        for (Servico servico : servicos) {
            comboBoxModel.addElement(servico);
        }
    }*/
    
    /*public Cliente obterCliente() {
        return (Cliente) view.getjComboBoxCliente().getSelectedItem();
    }*/
    
    /*public Servico obterServico() {
        return (Servico) view.getjComboBoxServico().getSelectedItem();
    }*/

    /*public void setarValor(float valor) {
        view.getTextValor().setText(valor+"");
    }*/

    @Override
    public Object obterModelo() {
        
        String nome = view.getTextNome().getText();
        
        String rg = view.getTextRg().getText();
        
        String telefone = view.getTextTelefone().getText();
        
        String data = view.getTextData().getText();
        
        String sexoCompleto = view.getjComboBoxSexo().getSelectedItem().toString(); 
        char sexo;
        if (sexoCompleto.equals("Masculino")){
            sexo = 'M';
        }else{
            sexo = 'F';
        }
        
        String email = view.getTextEmail().getText();
        
        String cep = view.getTextCep().getText();
        
        String endereco = view.getTextEndereco().getText();
        
        Cliente cliente = new Cliente(0, nome, sexo, data, telefone, email, rg, endereco, cep);
        
        return cliente;
    }

    @Override
    public void limparTela() {
        view.getTextCep().setText("");
        view.getTextData().setText("");
        view.getTextEmail().setText("");
        view.getTextEndereco().setText("");
        view.getTextNome().setText("");
        view.getTextRg().setText("");
        view.getTextTelefone().setText("");
    }

    public void preencherCampos(Cliente cliente) {
        view.getTextCep().setText(cliente.getCep());
        view.getTextData().setText(cliente.getDataFormatada());
        view.getTextEmail().setText(cliente.getEmail());
        view.getTextEndereco().setText(cliente.getEndereco());
        view.getTextNome().setText(cliente.getNome());
        view.getTextRg().setText(cliente.getRg());
        view.getTextTelefone().setText(cliente.getTelefone());
        String sexo;
        if(cliente.getSexo()=='M'){
            sexo = "Masculino";
        }else{
            sexo = "Feminino";
        }
        view.getjComboBoxSexo().setSelectedItem(sexo);
    }
}
