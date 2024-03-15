/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;

import Model.Agendamento;
import Model.Cliente;
import Model.Servico;
import View.Agenda;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;

/**
 *
 * @author henri
 */
public class AgendaHelper implements IHelper{
    
    private final Agenda view;

    public AgendaHelper(Agenda view) {
        this.view = view;
    }
    
    public void preencherTabela(ArrayList<Agendamento> agendamentos){
        DefaultTableModel tableModel = (DefaultTableModel) view.getTabelaAgendamentos().getModel();
        tableModel.setNumRows(0);
        
        //percorrer a lista preenchendo o table model
        for (Agendamento agendamento : agendamentos) {
            
            tableModel.addRow(new Object[]{
                agendamento.getId(),
                agendamento.getCliente().getNome(),
                agendamento.getServico().getDescricao(),
                agendamento.getValor(),
                agendamento.getDataFormatada(),
                agendamento.getHoraFormatada(),
                agendamento.getObservacao()
            });
            
        }
    }

    public void preencherClientes(ArrayList<Cliente> clientes) {
        DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) view.getjComboBoxCliente().getModel();
        
        for (Cliente cliente : clientes) {
            comboBoxModel.addElement(cliente);
        }
    }

    public void preencherServicos(ArrayList<Servico> servicos) {
        DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) view.getjComboBoxServico().getModel();
        
        for (Servico servico : servicos) {
            comboBoxModel.addElement(servico);
        }
    }
    
    public Cliente obterCliente() {
        return (Cliente) view.getjComboBoxCliente().getSelectedItem();
    }
    
    public Servico obterServico() {
        return (Servico) view.getjComboBoxServico().getSelectedItem();
    }

    public void setarValor(float valor) {
        view.getTextValor().setText(valor+"");
    }

    @Override
    public Object obterModelo() {
        
        Cliente cliente = obterCliente();
        
        Servico servico = obterServico();
        
        String valorString = view.getTextValor().getText();
        float valor = Float.parseFloat(valorString);
        
        String data = view.getTextData().getText();
        String hora = view.getTextHorario().getText();
        String dataHora = data + " " + hora;
        
        String observacao = view.getTextObservacao().getText();
        
        Agendamento agendamento = new Agendamento(0, cliente, servico, valor, dataHora, observacao);
        
        return agendamento;
    }

    @Override
    public void limparTela() {
        view.getTextData().setText("");
        view.getTextHorario().setText("");
        view.getTextObservacao().setText("");
    }

    public void preencherCampos(Agendamento agendamento) {
        view.getTextValor().setText(Float.toString(agendamento.getValor()));
        view.getTextData().setText(agendamento.getDataFormatada());
        view.getTextHorario().setText(agendamento.getHoraFormatada());
        view.getTextObservacao().setText(agendamento.getObservacao());
        view.getjComboBoxCliente().setSelectedItem(agendamento.getCliente());
        view.getjComboBoxServico().setSelectedItem(agendamento.getServico());
    }
    
    public boolean validarCampos(){
        String observacao = view.getTextObservacao().getText();
        
        String valorStr = view.getTextValor().getText();
        
        String data = view.getTextData().getText();
        
        String cliente = view.getjComboBoxCliente().getSelectedItem().toString(); 
        
        String servico = view.getjComboBoxServico().getSelectedItem().toString();
        
        String hora = view.getTextHorario().getText();
        
        if(observacao.equals("") || valorStr.equals("") || cliente.equals("") || data.equals("  /  /    ") || servico.equals("") || hora.equals("  :  ")){
            view.exibeMensagem("Preencha todos os campos!");
            return false;
        }else if(!isFloat(valorStr)){
            view.exibeMensagem("Valor precisa ser um número!");
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
            
            // Zerar informações de hora, minuto, segundo e milissegundo da dataAtual
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataAtual);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            dataAtual = cal.getTime();

            if(dataDigitada.before(dataAtual)){
                view.exibeMensagem("A data digitada já passou!");
                return false;       
            }else{
                if(!isFormatoHoraValido(hora)){
                    view.exibeMensagem("Formato de hora inválido!");
                    return false;  
                }       
            }  
        }
        return true;
    }
    
    // Função para verificar se uma string representa um número float
    public static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    //verificar se um horário é válido
    public static boolean isFormatoHoraValido(String horario) {
        if (horario.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            return true;
        } else {
            return false;
        }
    }
    
}
