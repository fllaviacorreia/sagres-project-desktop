package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.Aluno;

public class BancoAlunoGerenciar {
	Banco banco = new Banco();
	Connection conexao = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	private Statement consulta;
	private ResultSet resultSet1;

	public boolean BancoAlunoInserir(Aluno aluno) {
		int endereco = 0;
		int curso = 0;

		System.out.println("endereco id " + endereco);
		System.out.println("curso id " + curso);
		System.out.println("aluno curso " + aluno.getCurso());

		// aqui � o comando em sql que � necess�rio para inserir no banco de dados
		String sqlAluno = "INSERT INTO Aluno(nomeAluno, numMatricula, dataNascimento, email, telefone, celular,"
				+ "cpf, rg, ufRg, orgaoExpeditorRg, dataExpedicaoRg, Endereco_idEndereco, Curso_idCurso) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			endereco = new BancoEnderecoGerenciar().primeiroEultimo("id", 2);
			curso = Integer.parseInt(banco.consultar("Curso", "nomeCurso", aluno.getCurso(), "idCurso"));
		} catch (Exception e) {
			System.err.println("N�o foi poss�vel buscar endere�o e/ou curso: " + e);
		}
		// fazendo a conex�o com o banco de dados
		conexao = BancoConexao.open();

		try {

			// lincando o comando com o banco em aluno
			preparedStatement = conexao.prepareStatement(sqlAluno);

			// salvando na table os dados
			preparedStatement.setString(1, aluno.getNome());
			preparedStatement.setString(2, aluno.getMatricula());
			preparedStatement.setString(3, aluno.getDataNascimento());
			preparedStatement.setString(4, aluno.getEmail());
			preparedStatement.setString(5, aluno.getTelefone());
			preparedStatement.setString(6, aluno.getCelular());
			preparedStatement.setString(7, aluno.getCpf());
			preparedStatement.setString(8, aluno.getRg());
			preparedStatement.setString(9, aluno.getUf());
			preparedStatement.setString(10, aluno.getOrgaoExp());
			preparedStatement.setString(11, aluno.getDataExpedicao());

			// primeiro o endere�o � salvo e depois o id gerado de endere�o � jogado aqui
			// o mesmo acontece com curso, a diferen�a � que vai buscar o curso no banco
			preparedStatement.setInt(12, endereco);
			preparedStatement.setInt(13, curso);
			int teste = preparedStatement.executeUpdate();

			if (teste > 0) {
				preparedStatement.close();
				conexao.close();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Erro inserir aluno " + e);
		}

		return false;
	}

	public int contar(String campo, String query) {
		int qtd = 0;
		try {
			conexao = BancoConexao.open();
			consulta = conexao.createStatement();
			resultSet = consulta.executeQuery("SELECT COUNT(" + campo + ")FROM Aluno WHERE " + query);
			resultSet.first();
			qtd = this.resultSet.getInt("COUNT(" + campo + ")");
		} catch (SQLException e) {
			System.out.println("N�o foi poss�vel realizar a contagem!\n" + e.getMessage());
		}
		return qtd;
	}

	public void insereAlunosNoArray() {
		String sql = "SELECT * FROM Aluno";
		conexao = BancoConexao.open();
		try {
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				ArrayList<String> disciplinas = consultarUmaColuna("aluno_cursou_disciplina", "Disciplia_idDisciplina",
						"Aluno_idAluno", resultSet.getString("idAluno"));
				for (int i = 0; i < disciplinas.size(); i++) {
					String nomeDisciplina = new BancoDisciplinaGerenciar().consultar("idDisciplina", disciplinas.get(i),
							"nomeDisciplina");
					disciplinas.add(i, nomeDisciplina);
				}
				Aluno aluno = new Aluno(resultSet.getString("nomeAluno"), resultSet.getString("numMatricula"),
						resultSet.getString("dataNascimento"), resultSet.getString("email"),
						resultSet.getString("telefone"), resultSet.getString("celular"), resultSet.getString("cpf"),
						resultSet.getString("rg"), resultSet.getString("ufRg"), resultSet.getString("dataExpedicaoRg"),
						resultSet.getString("orgaoExpeditorRg"), new BancoCursoGerenciar().consultar("idCurso",
								resultSet.getString("Curso_idCurso"), "nomeCurso"),
						disciplinas);
				aluno.setId(resultSet.getInt("idAluno"));
			}
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int contar() {
		int qtd = 0;
		try {
			conexao = BancoConexao.open();
			consulta = conexao.createStatement();
			resultSet = consulta.executeQuery("SELECT COUNT(*)FROM Aluno");
			resultSet.first();
			qtd = this.resultSet.getInt("COUNT(*)");
		} catch (SQLException e) {
			System.out.println("N�o foi poss�vel realizar a contagem!\n" + e.getMessage());
		}
		return qtd;
	}

	public boolean BancoAlunoExcluir(String numMatricula) {// sugest�o mudar para boolean
		try {
			conexao = BancoConexao.open();
			String sql = "DELETE FROM Aluno  WHERE( numMatricula = '" + numMatricula + "')";
			preparedStatement = conexao.prepareStatement(sql);
			int teste = preparedStatement.executeUpdate(sql);
			if (teste > 0) {
				return true;
			}
			conexao.close();
		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Erro: N�o foi poss�vel excluir!\n" + e.getMessage());
			return false;
		}
		return true;
	}

	public int primeiroEultimo(String campo, int op) {
		int valor = 0;
		try {
			conexao = BancoConexao.open();
			if (op <= 0) {
				String sql = "SELECT MIN(" + campo + ") AS resultado FROM Aluno";
				preparedStatement = conexao.prepareStatement(sql);
				resultSet = preparedStatement.executeQuery(sql);
			} else {
				String sql = "SELECT MAX(" + campo + ") AS resultado FROM Aluno";
				preparedStatement = conexao.prepareStatement(sql);
				resultSet = preparedStatement.executeQuery(sql);
			}
			if (resultSet.next()) {
				valor = this.resultSet.getInt(1);
			}
			conexao.close();
		} catch (SQLException e) {
			System.out.println("N�o foi possivel realizar a pesquisar Firts/Last!\n" + e.getMessage());
		}
		return valor;
	}

	public String consultar(String chave, String valorChave, String campo) {
		String sql, retorno = "";
		sql = "SELECT " + campo + " FROM Aluno WHERE " + chave + " LIKE '%" + valorChave + "%'";
		try {
			conexao = BancoConexao.open();
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			if (resultSet.next()) {
				retorno = (String) resultSet.getString(campo);
			}
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro: N�o foi poss�vel consultar!\n" + e.getMessage());
		}
		return retorno;
	}

	public Aluno consultar_simples(String numMatricula) {
		String sql;
		Aluno aluno = null;
		sql = "SELECT * FROM Aluno WHERE numMatricula ='" + numMatricula + "'";
		try {
			conexao = BancoConexao.open();
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			if (resultSet.next()) {
				ArrayList<String> disciplinas = consultarUmaColuna("aluno_cursou_disciplina", "Disciplia_idDisciplina",
						"Aluno_idAluno", resultSet.getString("idAluno"));
				for (int i = 0; i < disciplinas.size(); i++) {
					String nomeDisciplina = new BancoDisciplinaGerenciar().consultar("idDisciplina", disciplinas.get(i),
							"nomeDisciplina");
					disciplinas.add(i, nomeDisciplina);
				}
				aluno = new Aluno(resultSet.getString("nomeAluno"), resultSet.getString("numMatricula"),
						resultSet.getString("dataNascimento"), resultSet.getString("email"),
						resultSet.getString("telefone"), resultSet.getString("celular"), resultSet.getString("cpf"),
						resultSet.getString("rg"), resultSet.getString("ufRg"), resultSet.getString("dataExpedicaoRg"),
						resultSet.getString("orgaoExpeditorRg"), new BancoCursoGerenciar().consultar("idCurso",
								resultSet.getString("Curso_idCurso"), "nomeCurso"),
						disciplinas);
				aluno.setId(resultSet.getInt("idAluno"));
			}
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro: N�o foi poss�vel consultar!\n" + e.getMessage());
		}
		return aluno;
	}

	public ArrayList<String> consultarUmaColuna(String tabela, String coluna) {
		ArrayList<String> lista = new ArrayList<String>();
		String sql = "SELECT * FROM " + tabela;
		conexao = BancoConexao.open();
		try {
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				lista.add(resultSet.getString(coluna));
			}
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<String> consultarUmaColuna(String tabela, String coluna, String campo, String valorCampo) {
		ArrayList<String> lista = new ArrayList<String>();
		String sql = "SELECT * FROM " + tabela + " WHERE " + campo + " = '" + valorCampo + "'";
		conexao = BancoConexao.open();
		try {
			preparedStatement = conexao.prepareStatement(sql);
			resultSet1 = preparedStatement.executeQuery(sql);
			while (resultSet1.next()) {
				lista.add(resultSet1.getString(coluna));
			}
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	public boolean atualizar(String tabela, String chave, String valor, String query) {
		try {
			conexao = BancoConexao.open();

			String sql = "UPDATE Aluno SET " + query + " WHERE " + chave + " = '" + valor + "'";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);

//			System.out.println(sql);
//			JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Atualiza��o",
//	     				JOptionPane.INFORMATION_MESSAGE);

			resultSet.close();
			conexao.close();
		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Erro: Não foi possivel atualizar!\n" + e.getMessage(),
					"Atualização", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean inserirHistoricoDisciplinas(String disciplinas, int idAluno, int idDisciplina, String media) {
		String sqlTableDisciplinasCursadas = "INSERT INTO aluno_cursou_disciplina(Aluno_idAluno, "
				+ "Disciplina_idDisciplina, mediaFinal) VALUES(?, ?, ?)";

		try {
			conexao = BancoConexao.open();
			preparedStatement = conexao.prepareStatement(sqlTableDisciplinasCursadas);
			preparedStatement.setInt(1, idAluno);
			preparedStatement.setInt(2, idDisciplina);
			preparedStatement.setString(3, media);
			System.out.println(preparedStatement);
			int teste = preparedStatement.executeUpdate();
			if (teste > 0) {
				preparedStatement.close();
				conexao.close();
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Erro inserir disciplina cursada por aluno " + e);
		}
		return false;
	}

	// alterar aluno (Guilherme)

	public boolean alterar_aluno(Aluno aluno) {

		int endereco = 0;
		int curso = 0;

		System.out.println("endereco id " + endereco);
		System.out.println("curso id " + curso);
		System.out.println("aluno curso " + aluno.getCurso());

		// aqui � o comando em sql que � necess�rio para alterar no banco de dados
		String sqlAluno = "UPDATE Aluno SET nomeAluno = ?, numMatricula = ?, dataNascimento = ?, email = ?, telefone = ?,"
				+ " celular = ?,cpf = ?, rg = ?, ufRg = ?, orgaoExpeditorRg = ?, dataExpedicaoRg = ?, Endereco_idEndereco = ?,"
				+ " Curso_idCurso  = ? WHERE numMatricula = ?";
		try {
			endereco = Integer.parseInt(banco.primeiroEultimo("Endereco", "idEndereco", 2));
			curso = Integer.parseInt(banco.consultar("Curso", "nomeCurso", aluno.getCurso(), "idCurso"));
		} catch (Exception e) {
			System.err.println("N�o foi poss�vel buscar endere�o e/ou curso: " + e);
		}
		// fazendo a conex�o com o banco de dados
		conexao = BancoConexao.open();

		try {

			// lincando o comando com o banco em aluno
			preparedStatement = conexao.prepareStatement(sqlAluno);

			// salvando na table os dados
			preparedStatement.setString(1, aluno.getNome());
			preparedStatement.setString(2, aluno.getMatricula());
			preparedStatement.setString(3, aluno.getDataNascimento());
			preparedStatement.setString(4, aluno.getEmail());
			preparedStatement.setString(5, aluno.getTelefone());
			preparedStatement.setString(6, aluno.getCelular());
			preparedStatement.setString(7, aluno.getCpf());
			preparedStatement.setString(8, aluno.getRg());
			preparedStatement.setString(9, aluno.getUf());
			preparedStatement.setString(10, aluno.getOrgaoExp());
			preparedStatement.setString(11, aluno.getDataExpedicao());

			// primeiro o endere�o � salvo e depois o id gerado de endere�o � jogado aqui
			// o mesmo acontece com curso, a diferen�a � que vai buscar o curso no banco
			preparedStatement.setInt(12, endereco);
			preparedStatement.setInt(13, curso);

			// coloca onde vai ser alterado
			preparedStatement.setString(14, aluno.getMatricula());
			int teste = preparedStatement.executeUpdate();

			if (teste > 0) {
				preparedStatement.close();
				conexao.close();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Erro inserir aluno " + e);
		}
		
		return false;

	}

}
