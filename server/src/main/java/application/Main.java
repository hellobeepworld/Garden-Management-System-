package application;

import bll.AuthenticationManager;
import bll.service.PlantService;
import bll.service.PlantedPlantService;
import bll.service.PlotService;
import bll.service.RoleService;
import bll.service.UserPlantRequestService;
import bll.service.UserService;
import bll.validators.PasswordValidator;
import gateway.PlantGateway;
import gateway.PlantedPlantGateway;
import gateway.PlotGateway;
import gateway.RoleGateway;
import gateway.UserGateway;
import gateway.UserPlantRequestGateway;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import request.ServerStarter;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        RoleService roleService = new RoleService(new RoleGateway());
        UserService userService = new UserService(new UserGateway());
        PlantService plantService = new PlantService(new PlantGateway());
        PlantedPlantService plantedPlantService = new PlantedPlantService(new PlantedPlantGateway(),
                plantService);
        PlotService plotService = new PlotService(new PlotGateway());
        UserPlantRequestService userPlantRequestService = new UserPlantRequestService(new UserPlantRequestGateway(),
                plantService);

        AuthenticationManager authenticationManager = new AuthenticationManager(userService,
                new BCryptPasswordEncoder(), new PasswordValidator());

        new ServerStarter(6666, 6667, userService,
                plantService, roleService, plotService,
                plantedPlantService, userPlantRequestService, authenticationManager);
    }
}
