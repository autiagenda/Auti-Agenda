package ifpr.pgua.eic.colecaoautiagenda;

import ifpr.pgua.eic.colecaoautiagenda.controllers.AgendamentoMedicamento;
import ifpr.pgua.eic.colecaoautiagenda.controllers.AgendamentoTarefaDiaria;
import ifpr.pgua.eic.colecaoautiagenda.controllers.AgendamentoTerapia;
import ifpr.pgua.eic.colecaoautiagenda.controllers.AtualizarMedicamento;
import ifpr.pgua.eic.colecaoautiagenda.controllers.AtualizarTarefaDiaria;
import ifpr.pgua.eic.colecaoautiagenda.controllers.AtualizarTerapia;
import ifpr.pgua.eic.colecaoautiagenda.controllers.CadastroResponsavel;
import ifpr.pgua.eic.colecaoautiagenda.controllers.CadastroUsuario;
import ifpr.pgua.eic.colecaoautiagenda.controllers.ListarMedicamentos;
import ifpr.pgua.eic.colecaoautiagenda.controllers.ListarTarefasDiarias;
import ifpr.pgua.eic.colecaoautiagenda.controllers.ListarTerapias;
import ifpr.pgua.eic.colecaoautiagenda.controllers.Login;
import ifpr.pgua.eic.colecaoautiagenda.controllers.MenuPrincipal;
import ifpr.pgua.eic.colecaoautiagenda.controllers.MenuPrincipalListar;
import ifpr.pgua.eic.colecaoautiagenda.controllers.Principal;
import ifpr.pgua.eic.colecaoautiagenda.daos.FabricaConexoes;
import ifpr.pgua.eic.colecaoautiagenda.daos.JDBCMedicamentoDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.JDBCResponsavelDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.JDBCTarefaDiariaDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.JDBCTerapiaDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.JDBCUsuarioDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.MedicamentoDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.ResponsavelDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.TarefaDiariaDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.TerapiaDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.UsuarioDAO;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioMedicamento;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioResponsavel;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTarefaDiaria;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioTerapia;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioUsuario;

public class App extends BaseAppNavigator {

        private UsuarioDAO usuarioDAO = new JDBCUsuarioDAO(FabricaConexoes.getInstance());
        private RepositorioUsuario repositorioUsuario = new RepositorioUsuario(usuarioDAO);

        private ResponsavelDAO responsavelDAO = new JDBCResponsavelDAO(FabricaConexoes.getInstance());
        private RepositorioResponsavel repositorioResponsavel = new RepositorioResponsavel(responsavelDAO);

        private TarefaDiariaDAO tarefaDiariaDAO = new JDBCTarefaDiariaDAO(FabricaConexoes.getInstance());
        private RepositorioTarefaDiaria repositorioTarefaDiaria = new RepositorioTarefaDiaria(tarefaDiariaDAO);

        private TerapiaDAO terapiaDAO = new JDBCTerapiaDAO(FabricaConexoes.getInstance());
        private RepositorioTerapia repositorioTerapia = new RepositorioTerapia(terapiaDAO);

        private MedicamentoDAO medicamentoDAO = new JDBCMedicamentoDAO(FabricaConexoes.getInstance());
        private RepositorioMedicamento repositorioMedicamento = new RepositorioMedicamento(medicamentoDAO);


        public static void main(String[] args) {
                launch();
        }

        @Override
        public String getHome() {
                return "LOGIN";
        }

        @Override
        public String getAppTitle() {
                return "AutiAgenda";
        }

        @Override
        public void registrarTelas() {
                registraTela("LOGIN", new ScreenRegistryFXML(App.class, "tela_principal.fxml", o -> new Principal()));

                registraTela("TELALOGIN", new ScreenRegistryFXML(App.class, "tela_login.fxml", o -> new Login(repositorioUsuario)));

                registraTela("CADASTROUSUARIO", new ScreenRegistryFXML(App.class, "tela_cadastro_usuario.fxml", o -> new CadastroUsuario(repositorioUsuario)));

                registraTela("CADASTRORESPONSAVEL", new ScreenRegistryFXML(App.class, "tela_cadastro_responsavel.fxml", o -> new CadastroResponsavel(repositorioResponsavel)));

                registraTela("MENUPRINCIPAL", new ScreenRegistryFXML(App.class, "tela_menu_principal.fxml", o -> new MenuPrincipal()));

                registraTela("AGENDAMENTOROTINADIARIA", new ScreenRegistryFXML(App.class, "tela_agendamento_rotina_diaria.fxml", o -> new AgendamentoTarefaDiaria(repositorioTarefaDiaria)));
                
                registraTela("AGENDAMENTOTERAPIA", new ScreenRegistryFXML(App.class, "tela_agendamento_terapia.fxml", o -> new AgendamentoTerapia(repositorioTerapia)));
                
                registraTela("AGENDAMENTOMEDICAMENTO", new ScreenRegistryFXML(App.class, "tela_agendamento_medicamento.fxml", o -> new AgendamentoMedicamento(repositorioMedicamento)));

                registraTela("MENUPRINCIPALLISTAR", new ScreenRegistryFXML(App.class, "tela_menu_principal_listar.fxml", o -> new MenuPrincipalListar()));

                registraTela("LISTARMEDICAMENTOS", new ScreenRegistryFXML(App.class, "tela_listar_medicamento.fxml", o -> new ListarMedicamentos()));
                
                registraTela("LISTARROTINADIARIA", new ScreenRegistryFXML(App.class, "tela_listar_rotina_diaria.fxml", o -> new ListarTarefasDiarias()));
                
                registraTela("LISTARTERAPIAS", new ScreenRegistryFXML(App.class, "tela_listar_terapia.fxml", o -> new ListarTerapias()));

                registraTela("ATUALIZARTAREFADIARIA", new ScreenRegistryFXML(App.class, "tela_atualizar_rotina_diaria.fxml", o -> new AtualizarTarefaDiaria()));

                registraTela("ATUALIZARMEDICAMENTO", new ScreenRegistryFXML(App.class, "tela_atualizar_medicamento.fxml", o -> new AtualizarMedicamento()));

                registraTela("ATUALIZARTERAPIA", new ScreenRegistryFXML(App.class, "tela_atualizar_terapia.fxml", o -> new AtualizarTerapia()));
        }
}
