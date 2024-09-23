package ifpr.pgua.eic.colecaoautiagenda;

import ifpr.pgua.eic.colecaoautiagenda.controllers.TelaPrincipal;
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
                registraTela("LOGIN", new ScreenRegistryFXML(App.class, "tela_principal.fxml", o -> new TelaPrincipal()));
        }
}
