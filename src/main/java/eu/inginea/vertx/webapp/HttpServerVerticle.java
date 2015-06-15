package eu.inginea.vertx.webapp;

import eu.inginea.vertx.demowebapp.DemoWebApp;
import eu.inginea.vertx.vertxsupport.ConfigBase;
import eu.inginea.vertx.vertxsupport.VerticleBase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import java.util.ArrayList;
import java.util.Collection;

public class HttpServerVerticle extends VerticleBase {

    private Config config;
    private Collection<WebAppRegistration> webApps;
    private WebAppHttpServer server;
    
    @Override
    public void start() throws Exception {
        super.init();
        this.config = new Config(context.config());
        server = new WebAppHttpServer(config, vertx);
        registerWebApps();
        server.router(buildMainRouter()).listen();
        logger.info(String.format("Listening to HTTP requests on %s:%d", server.getHost(), server.getPort()));
    }

    private void registerWebApps() {
        webApps = new ArrayList<>();
        webApps.add(new WebAppRegistration()
                .webApp(new DemoWebApp(config.getDemoWebAppConfig(), vertx))
                .path("/demo"));
    }

    private Router buildMainRouter() {
        Router mainRouter = Router.router(vertx);
        for (WebAppRegistration webAppReg : webApps) {
            mainRouter.mountSubRouter(webAppReg.path, webAppReg.webApp.getRouter());
        }
        return mainRouter;
    }

    static class Config extends ConfigBase {

        public Config(JsonObject config) {
            super(config);
        }

        int getPort() {
            return config.getInteger("port", 8080);
        }

        String getHostName() {
            return config.getString("hostname", "0.0.0.0");
        }

        private DemoWebApp.Config getDemoWebAppConfig() {
            return new DemoWebApp.Config(getSubConfig("demoWebApp"));
        }
    }
    
    static private class WebAppRegistration {
        String path;
        WebApp webApp;

        private WebAppRegistration webApp(DemoWebApp webApp) {
            this.webApp = webApp;
            return this;
        }

        private WebAppRegistration path(String path) {
            this.path = path;
            return this;
        }
    }
}
