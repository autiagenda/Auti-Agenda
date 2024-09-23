package ifpr.pgua.eic.colecaoautiagenda;

import ifpr.pgua.eic.colecaoautiagenda.controllers.CadastroUsuario;
import ifpr.pgua.eic.colecaoautiagenda.controllers.Login;
import ifpr.pgua.eic.colecaoautiagenda.controllers.Principal;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

public class App extends BaseAppNavigator {


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

                registraTela("TELALOGIN", new ScreenRegistryFXML(App.class, "tela_login.fxml", o -> new Login()));

                registraTela("CADASTROUSUARIO", new ScreenRegistryFXML(App.class, "tela_cadastro_usuario.fxml", o -> new CadastroUsuario()));
        }
}
