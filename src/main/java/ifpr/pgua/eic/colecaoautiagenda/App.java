package ifpr.pgua.eic.colecaoautiagenda;

import ifpr.pgua.eic.colecaoautiagenda.controllers.CadastroResponsavel;
import ifpr.pgua.eic.colecaoautiagenda.controllers.CadastroUsuario;
import ifpr.pgua.eic.colecaoautiagenda.controllers.Login;
import ifpr.pgua.eic.colecaoautiagenda.controllers.MenuPrincipal;
import ifpr.pgua.eic.colecaoautiagenda.controllers.MenuPrincipalListar;
import ifpr.pgua.eic.colecaoautiagenda.controllers.Principal;
import ifpr.pgua.eic.colecaoautiagenda.daos.FabricaConexoes;
import ifpr.pgua.eic.colecaoautiagenda.daos.JDBCResponsavelDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.JDBCUsuarioDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.ResponsavelDAO;
import ifpr.pgua.eic.colecaoautiagenda.daos.UsuarioDAO;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioResponsavel;
import ifpr.pgua.eic.colecaoautiagenda.repositories.RepositorioUsuario;

public class App extends BaseAppNavigator {

        private UsuarioDAO usuarioDAO = new JDBCUsuarioDAO(FabricaConexoes.getInstance());
        private RepositorioUsuario repositorioUsuario = new RepositorioUsuario(usuarioDAO);

        private ResponsavelDAO responsavelDAO = new JDBCResponsavelDAO(FabricaConexoes.getInstance());
        private RepositorioResponsavel repositorioResponsavel = new RepositorioResponsavel(responsavelDAO);

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

                // registraTela("MENUPRINCIPAL", new ScreenRegistryFXML(App.class, "tela_menu_principal.fxml", o -> new MenuPrincipal()));

               //registraTela("MENUPRINCIPALLISTAR", new ScreenRegistryFXML(App.class, "tela_menu_principal_listar.fxml", o -> new MenuPrincipalListar()));
        }
}
