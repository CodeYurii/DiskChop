package com.diskchop.controller;

import com.diskchop.model.dao.ClienteDao;
import com.diskchop.model.dao.EnderecoDao;
import com.diskchop.model.dao.TelefoneDao;
import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
import com.diskchop.model.entity.StatusCliente;
import com.diskchop.model.entity.Telefone;
import com.diskchop.model.util.MensagensSistema;
import com.diskchop.model.util.TelaMensagensSistema;
import com.diskchop.model.util.Validador;
import com.diskchop.model.util.erros.ClienteInvalidoException;
import com.diskchop.model.util.erros.EnderecoInvalidoException;
import com.diskchop.model.util.erros.ErrorHandler;
import com.diskchop.view.CadastroClientes;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CadastroClientesController {
    private CadastroClientes view;
    private Cliente cliente;
    private ClienteDao clienteDao;// Model
    private Validador validador;
    private Endereco endereco;
    private EnderecoDao enderecoDao;
    private Telefone telefone;
    private TelefoneDao telefoneDao;

    public CadastroClientesController(CadastroClientes view) {
        this.view = view;
        this.cliente = new Cliente();
        this.clienteDao = new ClienteDao();
        this.validador = new Validador();
        this.endereco = new Endereco();
        this.enderecoDao = new EnderecoDao();
        this.telefone = new Telefone();
        this.telefoneDao = new TelefoneDao();
        configureActions();
        configurarTela();
    }

    public void configureActions(){
        view.getBotaoCadVoltar().addActionListener(e -> {voltarMenu();});
        view.getBotaoCadLimpar().addActionListener(e -> limparCampos());
        view.getBotaoCadClienteAdicionar().addActionListener(e -> {salvarCliente();});
        view.getBotaoCadClienteProcurar().addActionListener(e -> buscarClienteId());
        view.getBotaoCadClienteDelete().addActionListener(e -> excluirCliente());
        view.getBotaoCadClienteSalvar().addActionListener(e -> {editarCliente();});

        view.getBotaoCadEnderecoAdicionar().addActionListener(e -> {salvarEndereco();});
        view.getBotaoCadEnderecoProcurar().addActionListener(e -> buscarEnderecoCLiente());




        view.getBotaoCadTelefoneAdicionar().addActionListener(e -> {salvarTelefone();});
        view.getBotaoCadTelefoneProcurar().addActionListener(e -> buscarTelefoneCLiente());
        //view.getBotaoCadEnderecoEditar().addActionListener(e -> editarEndereco());

        view.getTextFieldCadNome().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarClienteNome();
                }
            }
        });
        view.getTextFieldCadCpf().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarClienteCpf();
                }
            }
        });

    }

    private void configurarTela(){
        view.getPainelCenter().setBackground(new Color(40, 40, 40));
        view.getPainelTop().setBackground(new Color(40, 40, 40));
        view.getPainelBottom().setBackground(new Color(40, 40, 40));
        view.getPainelCadClientes().setBackground(new Color(40, 40, 40));
        view.getPainelCadEnderecos().setBackground(new Color(40, 40, 40));
        view.getPainelCadTelefones().setBackground(new Color(40, 40, 40));
        view.getLabelCadTitulo().setForeground(new Color(217, 231, 196));
        view.getTextFieldCadNome().setBackground(new Color(40, 40, 40));
        view.getTextFieldCadNome().setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
        view.getTextFieldCadNome().setForeground(new Color(255, 255, 255));
        setTextFields();
        view.getScrollPaneCadClientes().setBackground(new Color(40, 40, 40));
        view.getScrollPaneCadClientes().getViewport().setBackground(new Color(40, 40, 40));
        view.getScrollPaneCadEnderecos().getViewport().setBackground(new Color(40, 40, 40));
        view.getScrollPaneCadTelefones().getViewport().setBackground(new Color(40, 40, 40));
        view.getScrollPaneCadEnderecos().setBackground(new Color(40, 40, 40));
        view.getScrollPaneCadTelefones().setBackground(new Color(40, 40, 40));
        view.getTableCadClientes().setBackground(new Color(40, 40, 40));
        view.getTableCadClientes().setForeground(new Color(255, 255, 255));
        view.getTableCadClientes().setRowHeight(22);
        view.getTableCadEnderecos().setBackground(new Color(40, 40, 40));
        view.getTableCadTelefones().setBackground(new Color(40, 40, 40));
        view.getTabbedPaneCad().setBackground(new Color(40, 40, 40));
        view.getTableCadClientes().setOpaque(true);
        view.getTableCadClientes().getTableHeader().setBackground(new Color(63, 63, 63));
        view.getTableCadClientes().getTableHeader().setForeground(Color.WHITE);
        view.getTableCadClientes().getTableHeader().setFont(new Font ("Roboto", Font.BOLD, 12));
        view.getTableCadEnderecos().setOpaque(true);
        view.getTableCadEnderecos().getTableHeader().setBackground(new Color(63, 63, 63));
        view.getTableCadEnderecos().getTableHeader().setForeground(Color.WHITE);
        view.getTableCadEnderecos().getTableHeader().setFont(new Font ("Roboto", Font.BOLD, 12));
        view.getTableCadEnderecos().setBackground(new Color(40, 40, 40));
        view.getTableCadEnderecos().setForeground(new Color(255, 255, 255));
        view.getTableCadEnderecos().setRowHeight(22);
        view.setBackground(new Color(40, 40, 40));
        view.getBotaoCadVoltar().setBackground(new Color(18, 18, 18));
    }
    private void setTextFields(){
        JTextField[] fields = view.getTodosJTextFields();
        for(JTextField field : fields){
            field.setBackground(new Color(40, 40, 40));
            field.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
            field.setForeground(new Color(255, 255, 255));
        }
    }

    /*** funcionalidades ***/
    public void voltarMenu(){
        view.dispose();
    }

    private void limparCampos(){
        for (JTextField textField : view.getTodosJTextFields()) {
            textField.setText("");
        }
    }

    /*** funções edição ***/

 /*
    private void editarEndereco(){
        try{
            Endereco editarEndereco = new Endereco();
            editarEndereco = carregarNovoEndereco();
            String cpf = view.getTextFieldCadCpfString();
            //Cliente cliente1 = clienteDao.buscarPorCpf(cpf);
            endereco.setIdEndereco(view.getTextFieldCadIdLong());
            cliente1.getEnderecos().add(endereco);
            clienteDao.salvar(cliente1);
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
    }*/

    private void editarTelefone(){}



    /*** funções Cliente***/
    private void salvarCliente() {
       try {
           Cliente novoCliente;
           novoCliente = carregarNovoCliente();
           validador.validarCliente(novoCliente);

           if (clienteDao.isCpfCadastrado(novoCliente.getCpf())) {
               TelaMensagensSistema.mostrarErro(MensagensSistema.CLIENTE_CPF_EXISTENTE);
               return;
           }
           clienteDao.salvarCliente(novoCliente);
           TelaMensagensSistema.mostrarInformacao(MensagensSistema.CLIENTE_CADASTRO_SUCESSO);
           Long idCliente = clienteDao.buscarIdPorCpf(view.getTextFieldCadCpf().getText());
           view.getTextFieldCadId().setText(idCliente.toString());
       } catch (ClienteInvalidoException e) {
           TelaMensagensSistema.mostrarErro(e.getMessage());
       } catch (Exception e) {
           ErrorHandler.handle(e);
       }
    }



    private void editarCliente(){
        try{
            Cliente editarCliente;
            editarCliente = carregarNovoCliente();
            editarCliente.setIdCliente(view.getTextFieldCadIdLong());
            validador.validarCliente(editarCliente);
            clienteDao.atualizarCliente(editarCliente);
            TelaMensagensSistema.mostrarInformacao(MensagensSistema.CLIENTE_CADASTRO_SUCESSO);
        } catch (ClienteInvalidoException e) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
    }

    private void excluirCliente(){
        try{
            TelaMensagensSistema.mostrarConfirmacao("Voce tem certeza?");
            Cliente editarCliente;
            editarCliente = carregarNovoCliente();
            editarCliente.setIdCliente(view.getTextFieldCadIdLong());
            clienteDao.excluirCliente(editarCliente.getIdCliente());
            TelaMensagensSistema.mostrarInformacao(MensagensSistema.CLIENTE_CADASTRO_SUCESSO);
        } catch (ClienteInvalidoException e) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
    }

    private void buscarClienteId(){
        DefaultTableModel novoModelo = new DefaultTableModel(
                new Object[][] {}, // Dados inicialmente vazios
                new String[] {"ID", "Nome", "CPF", "Regime", "Status", "Data_Cadastro", "Obs"} 
        );
        view.getTableCadClientes().setModel(novoModelo);
        novoModelo.setRowCount(0);
        Long id = view.getTextFieldCadIdLong();
        Cliente cliente1;
        cliente1 = clienteDao.buscarClientePorId(id);
        Object[] rowData = {
                cliente1.getIdCliente(),
                cliente1.getNome(),
                cliente1.getCpf(),
                cliente1.getRegime(),
                cliente1.getStatusCliente(),
                cliente1.getDataCadastroCliente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                cliente1.getObservacao()
        };
        novoModelo.addRow(rowData);
        escreverCampos(cliente1);
    }

    public void escreverCampos(Cliente cliente){
        view.getTextFieldCadId().setText(cliente.getIdCliente().toString());
        view.getTextFieldCadNome().setText(cliente.getNome());
        view.getTextFieldCadCpf().setText(cliente.getCpf());
        view.getTextFieldCadObservacaoCliente().setText(cliente.getObservacao());
    }


    private void buscarClienteNome(){
        DefaultTableModel novoModelo = new DefaultTableModel(
                new Object[][] {}, // Dados inicialmente vazios
                new String[] {"ID", "Nome", "CPF", "Regime", "Status", "Data_Cadastro", "Obs"} // Cabeçalhos das colunas
        );
        view.getTableCadClientes().setModel(novoModelo);
        novoModelo.setRowCount(0);
        String nome = view.getTextFieldCadNome().getText().trim().toUpperCase();
        List<Cliente>cliente;
        cliente = clienteDao.buscarClientePorNome(nome);
        for (Cliente cliente1 : cliente) {
            Object[] rowData = {
                    cliente1.getIdCliente(),
                    cliente1.getNome(),
                    cliente1.getCpf(),
                    cliente1.getRegime(),
                    cliente1.getStatusCliente(),
                    cliente1.getDataCadastroCliente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    cliente1.getObservacao()
            };
            novoModelo.addRow(rowData);
        }
    }

    public void buscarClienteCpf(){
        DefaultTableModel novoModelo = new DefaultTableModel(
                new Object[][] {}, // Dados inicialmente vazios
                new String[] {"ID", "Nome", "CPF", "Regime", "Status", "Data_Cadastro", "Obs"} // Cabeçalhos das colunas
        );
        view.getTableCadClientes().setModel(novoModelo);
        novoModelo.setRowCount(0);
        String cpf = view.getTextFieldCadCpf().getText().trim();
        Cliente cliente1;
        cliente1 = clienteDao.buscarClientePorCpf(cpf);
        Object[] rowData = {
                cliente1.getIdCliente(),
                cliente1.getNome(),
                cliente1.getCpf(),
                cliente1.getRegime(),
                cliente1.getStatusCliente(),
                cliente1.getDataCadastroCliente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                cliente1.getObservacao()
        };
        novoModelo.addRow(rowData);
    }

    /*** funções Endereço ***/
    private void salvarEndereco() {
        try {
            Endereco novoEndereco;
            Long idCliente = view.getTextFieldCadIdLong();
            novoEndereco = carregarNovoEndereco();
            validador.validarEndereco(novoEndereco);
            enderecoDao.adicionarEnderecoCliente(idCliente, novoEndereco);
            TelaMensagensSistema.mostrarInformacao(MensagensSistema.ENDERECO_CADASTRO_SUCESSO);
        } catch (EnderecoInvalidoException e) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }


    /*** funções Telefone ***/
    private void salvarTelefone() {
        try {
            Telefone novoTelefone = new Telefone();
            String cpf = view.getTextFieldCadCpfString();
            Long idCliente = clienteDao.buscarIdPorCpf(cpf);
            novoTelefone = carregarNovoTelefone();
            telefoneDao.adicionarTelefoneCliente(idCliente, novoTelefone);
            TelaMensagensSistema.mostrarInformacao(MensagensSistema.CLIENTE_CADASTRO_SUCESSO);
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    /*** funções carregar ***/
    private Telefone carregarNovoTelefone(){
        Telefone novoTelefone = new Telefone();
        String telefoneNumero = view.getTextFieldCadTelefone().getText();
        novoTelefone.setTelefone(telefoneNumero);
        String contato = view.getTextFieldCadContato().getText();
        novoTelefone.setContato(contato);
        if (novoTelefone.getDataCadastroTelefone() == null) {novoTelefone.setDataCadastroTelefone();}
        return novoTelefone;
    }

    private Cliente carregarNovoCliente(){
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(view.getTextFieldCadNomeString().toUpperCase());
        novoCliente.setCpf(view.getTextFieldCadCpfString());
        novoCliente.setObservacao(view.getTextFieldCadObservacaoClienteString().toUpperCase());
        novoCliente.setRegime(view.getComboCadPessoa());
        novoCliente.setStatusCliente(StatusCliente.ATIVO);
        if (novoCliente.getDataCadastroCliente() == null) {novoCliente.setDataCadastroCliente();}
        return novoCliente;
    }

    private Endereco carregarNovoEndereco() {
        Endereco novoEndereco = new Endereco();
        novoEndereco.setLogradouro(view.getTextFieldCadLogradouroString());
        novoEndereco.setNumero(view.getTextFieldCadNumeroString());
        novoEndereco.setBairro(view.getTextFieldCadBairroString());
        novoEndereco.setCidade(view.getTextFieldCadCidadeString());
        novoEndereco.setUf(view.getTextFieldCadUfString());
        novoEndereco.setCep(view.getTextFieldCadCepString());
        novoEndereco.setComplemento(view.getTextFieldCadComplementoString());
        novoEndereco.setTipo(view.getComboCadTipo());
        novoEndereco.setObservacaoEndereco(view.getTextFieldCadObservacaoEnderecoString());
        if (novoEndereco.getDataCadastroEndereco() == null) {novoEndereco.setDataCadastroEndereco();}
        return novoEndereco;
    }

    /*** funções de busca ***/


    public void buscarEnderecoCLiente(){
        DefaultTableModel novoModelo = new DefaultTableModel(
                new Object[][] {}, // Dados inicialmente vazios
                new String[] {"ID", "Logradouro", "Numero", "Bairro", "Cidade", "Uf",
                        "CEP", "Complemento","Tipo", "Data_Cadastro", "Obs"} // Cabeçalhos das colunas
        );

        view.getTableCadEnderecos().setModel(novoModelo);
        novoModelo.setRowCount(0);
        Long idCliente = view.getTextFieldCadIdLong();
        List<Endereco>enderecos;
        enderecos = enderecoDao.buscarEnderecosDoCliente(idCliente);


        if (enderecos != null && !enderecos.isEmpty()) {
            for (Endereco endereco : enderecos) {
                Object[] rowData = {
                        endereco.getIdEndereco(),
                        endereco.getLogradouro(),
                        endereco.getNumero(),
                        endereco.getBairro(),
                        endereco.getCidade(),
                        endereco.getUf(),
                        endereco.getCep(),
                        endereco.getComplemento(),
                        endereco.getTipo(),
                        endereco.getDataCadastroEndereco().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        endereco.getObservacaoEndereco()
                };

                // Adicionando linha ao modelo
                novoModelo.addRow(rowData);
            }
        }
    }

    public void buscarTelefoneCLiente(){
        DefaultTableModel novoModelo = new DefaultTableModel(
                new Object[][] {}, // Dados inicialmente vazios
                new String[] {"ID", "Telefone", "Contato", "Data_Cadastro"} // Cabeçalhos das colunas
        );
        view.getTableCadTelefones().setModel(novoModelo);
        novoModelo.setRowCount(0);
        Long idCliente = view.getTextFieldCadIdLong();
        List<Telefone>telefones;
        telefones = telefoneDao.buscarTelefonesDoCliente(idCliente);


        if (telefones != null && !telefones.isEmpty()) {
            for (Telefone telefone : telefones) {
                Object[] rowData = {
                        telefone.getIdTelefone(),
                        telefone.getTelefone(),
                        telefone.getContato(),
                        telefone.getDataCadastroTelefone().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                };
                novoModelo.addRow(rowData);
            }
        }
    }

    private void tratarExcecao(Exception e) {
        if (e instanceof IllegalArgumentException) {
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } else if (e instanceof ConstraintViolationException) {
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_VIOLACAO_RESTRICAO);
        } else if (e instanceof PersistenceException) {
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_BANCO_DADOS);
        } else {
            e.printStackTrace(); // Apenas para logs
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_GENERICO);
        }
    }


}
