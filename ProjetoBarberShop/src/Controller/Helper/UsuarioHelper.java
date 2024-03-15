/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;


import Model.Usuario;
import View.UsuarioView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author henri
 */
public class UsuarioHelper implements IHelper{
     private final UsuarioView view;

    public UsuarioHelper(UsuarioView view) {
        this.view = view;
    }
    
    public boolean validarCampos(){
        String nome = view.getTextNome().getText();
        
        String rg = view.getTextRg().getText();
        
        String telefone = view.getTextTelefone().getText();
        
        String data = view.getTextData().getText();
        
        String sexoCompleto = view.getjComboBoxSexo().getSelectedItem().toString(); 
        
        String email = view.getTextEmail().getText();
        
        String senha = view.getTextSenha().getText();
        
        String grauAcesso = view.getjComboBoxNivelAcesso().getSelectedItem().toString();
        
        if(nome.equals("") || rg.equals("") || telefone.equals("(  )         ") || data.equals("  /  /    ") || email.equals("") || senha.equals("") || grauAcesso.equals("")){
            view.exibeMensagem("Preencha todos os campos!");
            return false;
        }else if(rg.length() != 10){
            view.exibeMensagem("O RG está incompleto!");
            return false;
        }else if(senha.length() < 8){
            view.exibeMensagem("A senha precisa ter mais de 8 caracteres!");
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
    
    public void preencherTabela(ArrayList<Usuario> usuarios){
        DefaultTableModel tableModel = (DefaultTableModel) view.getTabelaUsuarios().getModel();
        tableModel.setNumRows(0);
        
        //percorrer a lista preenchendo o table model
        for (Usuario usuario : usuarios) {
            
            tableModel.addRow(new Object[]{
                usuario.getId(),
                usuario.getNome(),
                usuario.getRg(),
                usuario.getTelefone(),
                usuario.getDataFormatada(),
                usuario.getSexo(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getNivelAcesso()
            });
            
        }
    }

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
        
        String senha = view.getTextSenha().getText();
        
        String nivelAcesso = view.getjComboBoxNivelAcesso().getSelectedItem().toString();
        
        Usuario usuario = new Usuario(0, nome, sexo, data, telefone, email, rg, senha, nivelAcesso);
        
        return usuario;
    }

    @Override
    public void limparTela() {
        view.getTextSenha().setText("");
        view.getTextData().setText("");
        view.getTextEmail().setText("");
        view.getTextNome().setText("");
        view.getTextRg().setText("");
        view.getTextTelefone().setText("");
    }

    public void preencherCampos(Usuario usuario) {
        view.getTextSenha().setText(usuario.getSenha());
        view.getTextData().setText(usuario.getDataFormatada());
        view.getTextEmail().setText(usuario.getEmail());
        view.getjComboBoxNivelAcesso().setSelectedItem(usuario.getNivelAcesso());
        view.getTextNome().setText(usuario.getNome());
        view.getTextRg().setText(usuario.getRg());
        view.getTextTelefone().setText(usuario.getTelefone());
        String sexo;
        if(usuario.getSexo()=='M'){
            sexo = "Masculino";
        }else{
            sexo = "Feminino";
        }
        view.getjComboBoxSexo().setSelectedItem(sexo);
    }
}
