package com.faiskaburguer.controllers.gerenciadores.editores;

import com.faiskaburguer.db.dal.EmpresaDAL;
import com.faiskaburguer.db.dal.EnderecoDAL;
import com.faiskaburguer.db.entidade.Empresa;
import com.faiskaburguer.db.entidade.Endereco;
import com.faiskaburguer.db.util.Mascaras;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.faiskaburguer.db.util.Mascaras.*;


public class EditarEmpresa {

    @FXML
    private TextField emp_razao_field;

    @FXML
    private TextField emp_fantasia_field;

    @FXML
    private TextField emp_cnpj_field;

    @FXML
    private TextField emp_telefone_field;

    @FXML
    private TextField emp_email_field;

    @FXML
    private TextField emp_cep_field;

    @FXML
    private TextField emp_rua_field;

    @FXML
    private TextField emp_valoremb_field;

    @FXML
    private TextField emp_nrua_field;

    private Empresa empresa;


    public EditarEmpresa(Empresa empresa){
        this.empresa = empresa;
    }

    @FXML
    protected void initialize() {
        if (empresa != null) {
            emp_razao_field.setText(empresa.getEmp_razao());
            emp_fantasia_field.setText(empresa.getEmp_fantasia());
            emp_cnpj_field.setText(empresa.getEmp_cnpj());
            emp_telefone_field.setText(empresa.getEmp_fone());
            emp_email_field.setText(empresa.getEmp_email());
            if(empresa.getEndereco()!=null){
                emp_cep_field.setText(empresa.getEndereco().getCep());
                emp_rua_field.setText(empresa.getEndereco().getRua());
                emp_nrua_field.setText(empresa.getEndereco().getNumero());
            }
            emp_valoremb_field.setText(empresa.getEmp_vlremb().toString());
        }
    }

    @FXML
    protected void CadastrarEmpresa(){
        empresa.setEmp_razao(emp_razao_field.getText());
        empresa.setEmp_fantasia(emp_fantasia_field.getText());
        empresa.setEmp_cnpj(Mascaras.mascCnpj(emp_cnpj_field.getText()));
        empresa.setEmp_fone(Mascaras.mascFone(emp_telefone_field.getText()));
        empresa.setEmp_email(emp_email_field.getText());
        empresa.setEndereco(new Endereco(emp_cep_field.getText(),emp_rua_field.getText(),emp_nrua_field.getText()));
        empresa.setEmp_vlremb(Double.parseDouble(emp_valoremb_field.getText()));

        EnderecoDAL enderecoDAL = new EnderecoDAL();
        if(enderecoDAL.gravar(empresa.getEndereco())){
            EmpresaDAL empresaDAL = new EmpresaDAL();
            empresaDAL.gravar(empresa);
        }
    }


}
