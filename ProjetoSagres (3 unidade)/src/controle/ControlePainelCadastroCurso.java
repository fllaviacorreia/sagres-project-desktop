package controle;

/**
 *  @author Fl�via de Jesus Correia
 *  @author Ian Farias
 *  
 * */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import banco.BancoCursoGerenciar;
import modelo.Curso;
import visao.VisaoFramePrincipal;
import visao.VisaoPainelCadastroCurso;
import visao.VisaoPainelFluxograma;

public class ControlePainelCadastroCurso implements ActionListener {
	static VisaoPainelCadastroCurso telaCadCurso;
	static VisaoPainelFluxograma telaFluxograma;
	static VisaoFramePrincipal framePrincipal;
	Curso dados;
	private int contador;
	private int saida;
	private int volta;
	private int cargaHoraria;
	private String cargaHorariaTotal;
	private String nome;
	private String tipoCurso;
	private String semestres;

	public ControlePainelCadastroCurso(VisaoFramePrincipal framePrincipal, VisaoPainelCadastroCurso telaCadCurso, int volta) {
		this.framePrincipal = framePrincipal;
		ControlePainelCadastroCurso.telaCadCurso = telaCadCurso;
		this.volta = volta;
		telaCadCurso.setVisible(true);
		AddEventos();
		// preecheComboBox();
	}

	public void AddEventos() {
		telaCadCurso.getButtonAdicionarCargaHoraria().addActionListener(this);
		telaCadCurso.getButtonCancelar().addActionListener(this);
		telaCadCurso.getButtonConfirmar().addActionListener(this);
		telaCadCurso.getButtonVoltar().addActionListener(this);
		telaCadCurso.getRdbtnLicenciatura().addActionListener(this);
		telaCadCurso.getRdbtnBacharelado().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == telaCadCurso.getButtonVoltar()) {
			try {
				saida = JOptionPane.showConfirmDialog(telaCadCurso, "Deseja realmente voltar?", "Confirma��o de sa�da",
						JOptionPane.YES_NO_OPTION);
				if (saida == 0) {
					LimpaDados();
					if (volta == 1)
						ControlePainelTelaInicial.troca();
					else if (volta == 2)
						ControlePainelCadastro.troca();
					else
						ControlePainelConsulta.troca();
				}
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		}

		if (e.getSource() == telaCadCurso.getButtonCancelar()) {
			try {
				saida = JOptionPane.showConfirmDialog(telaCadCurso, "Deseja realmente cancelar o cadastro?",
						"Confirma��o de sa�da", JOptionPane.YES_NO_OPTION);
				if (saida == 0) {
					LimpaDados();
				//	ControlePainelCadastro.troca();
				}
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		}
		if (e.getSource() == telaCadCurso.getButtonAdicionarCargaHoraria()) {
			try {
				cargaHoraria = Integer.parseInt(JOptionPane.showInputDialog(telaCadCurso, "Insira a carga hor�ria:",
						"Inserir", JOptionPane.OK_CANCEL_OPTION));

				if (!String.valueOf(cargaHoraria).isEmpty()) {
					telaCadCurso.getComboBoxCargaHorariaTotal().addItem(String.valueOf(cargaHoraria));
					telaCadCurso.getComboBoxCargaHorariaTotal()
							.setSelectedIndex(telaCadCurso.getComboBoxCargaHorariaTotal().getItemCount() - 1);
				}
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		}
		if(e.getSource() == telaCadCurso.getRdbtnLicenciatura()) {
			if(telaCadCurso.getRdbtnLicenciatura().isSelected()) {
				telaCadCurso.getRdbtnBacharelado().setSelected(false);
				telaCadCurso.getRdbtnBacharelado().setEnabled(false);
			}else {
				telaCadCurso.getRdbtnBacharelado().setEnabled(true);
			}
		}if(e.getSource() ==telaCadCurso.getRdbtnBacharelado()) {
			if(telaCadCurso.getRdbtnBacharelado().isSelected()) {
				telaCadCurso.getRdbtnLicenciatura().setSelected(false);
				telaCadCurso.getRdbtnLicenciatura().setEnabled(false);
			}else {
				telaCadCurso.getRdbtnLicenciatura().setEnabled(true);
			}
		}
		if (e.getSource() == telaCadCurso.getButtonConfirmar()) {
			try {
				contador = 0;
				if (!telaCadCurso.getFormattedTextFieldNomeCurso().getText()
						.equals("                                                       ")) {
					nome = telaCadCurso.getFormattedTextFieldNomeCurso().getText().toString().toUpperCase();
					if (verificaNomes(nome)) {
						JOptionPane.showMessageDialog(telaCadCurso, "Nome j� cadastrado!", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					} else {
						contador++;
					}

				}
				if (!telaCadCurso.getComboBoxSemestresTotais().getSelectedItem().equals("SELECIONE")) {
					semestres =telaCadCurso.getComboBoxSemestresTotais().getSelectedItem().toString();
					contador++;
				}
				if (!telaCadCurso.getComboBoxCargaHorariaTotal().getSelectedItem().equals("SELECIONE")) {
					cargaHorariaTotal = telaCadCurso.getComboBoxCargaHorariaTotal().getSelectedItem().toString();
					contador++;
				}
				if (!telaCadCurso.getComboBoxHorario().getSelectedItem().equals("SELECIONE")) {
					tipoCurso = telaCadCurso.getComboBoxHorario().getSelectedItem().toString();
					contador++;
				}
				if(telaCadCurso.getRdbtnBacharelado().isSelected() || telaCadCurso.getRdbtnLicenciatura().isSelected()) {
					contador++;
				}
				if (contador == 5) {
					dados = new Curso(cargaHorariaTotal, nome, tipoCurso, semestres, 
							telaCadCurso.getRdbtnLicenciatura().isSelected(), telaCadCurso.getRdbtnBacharelado().isSelected());
					// new ControleArquivo(3);
					BancoCursoGerenciar bancoCurso = new BancoCursoGerenciar();
					boolean retorno1 = bancoCurso.BancoCursoInserir(dados);

					if (retorno1)
						JOptionPane.showMessageDialog(telaCadCurso, "Cadastro realizado com sucesso.");

					LimpaDados();
				} else {
					JOptionPane.showMessageDialog(telaCadCurso, "TODOS OS CAMPOS DEVEM SER PREENCHIDOS!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		}
		
	}

	// m�todo que preenche o comboBox curso com os adicionados e cadastrados ta
	// telaDisciplina

//	public void preecheComboBox() {
//		telaCadCurso.getComboBoxCurso().addItem("SELECIONE");
//		ArrayList<String> disc = new ArrayList<String>();
//		String dis;
//		dis = Main.disciplina.get(0).getCurso();
//		disc.add(dis);
//		int cont;
//		for(int i = 1; i < Main.disciplina.size(); i++) {
//			cont = 0;
//			dis = Main.disciplina.get(i).getCurso().toString();
//			
//			if(disc.get(i).equals(dis)) {
//				cont ++;
//			}
//			if(cont == 0)
//				disc.add(dis);
//		}
//		
//		for(int i = 0; i<disc.size(); i++) {
//			telaCadCurso.getComboBoxCurso().addItem(disc.get(i).toString());
//		}
//	}

	public boolean verificaNomes(String nome) {
		ArrayList<String> cursoNomes = new BancoCursoGerenciar().consultarUmaColuna("Curso", "nomeCurso");
		for (int i = 0; i < cursoNomes.size(); i++) {
			if (cursoNomes.get(i).equals(nome)) {
				return true;
			}
		}
		return false;
	}

	public void LimpaDados() {
		telaCadCurso.getRdbtnBacharelado().setSelected(false);
		telaCadCurso.getRdbtnBacharelado().setEnabled(true);
		telaCadCurso.getRdbtnLicenciatura().setSelected(false);
		telaCadCurso.getRdbtnLicenciatura().setEnabled(true);
		telaCadCurso.getFormattedTextFieldNomeCurso().setText(null);
		telaCadCurso.getComboBoxCargaHorariaTotal().setSelectedIndex(0);
		telaCadCurso.getComboBoxHorario().setSelectedIndex(0);
//		telaCadCurso.getComboBoxDisciplinas().removeAllItems();
//		telaCadCurso.getComboBoxCurso().removeAllItems();
		telaCadCurso.getComboBoxSemestresTotais().setSelectedIndex(0);
		
	}

}
