package com.yevgen.logcollectionmaster.api;

import com.yevgen.logcollectionmaster.model.Server;
import com.yevgen.logcollectionmaster.service.IRegistrarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/")
public class RegistrarController {

    private static Logger logger = LoggerFactory.getLogger(RegistrarController.class);

    @Autowired
    private IRegistrarService registerService;

    @PostMapping({ "/register/{host}", "/register/{host}/{port}" })
    public ResponseEntity<String> registerHost(
            @NotBlank @PathVariable(value = "host") String host,
            @PathVariable(value = "port", required = false) String port) {

        Server server = new Server(host, port);
        logger.info("Register {}", server);

        try {
            registerService.register(server);
            return new ResponseEntity<>(String.format("%s registered successfully", server), HttpStatus.OK);
        }
        catch (Exception e) {
            String message = String.format("Error during registering %s", server);
            logger.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping({ "/unregister/{host}", "/unregister/{host}/{port}" })
    public ResponseEntity<String> unregisterHost(
            @NotBlank @PathVariable(value = "host") String host,
            @PathVariable(value = "port", required = false) String port) {

        Server server = new Server(host, port);
        logger.info("Unregister {}", server);

        try {
            registerService.unregister(server);
            return new ResponseEntity<>(String.format("%s unregistered successfully", server), HttpStatus.OK);
        }
        catch (Exception e) {
            String message = String.format("Error during unregistering %s", server);
            logger.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/servers")
    public ResponseEntity<List<Server>> getServers() {

        logger.info("Get servers list");

        try {
            List<Server> servers = registerService.getServers();
            return new ResponseEntity<>(servers, HttpStatus.OK);
        }
        catch (Exception e) {
            String message = String.format("Error during getting servers list");
            logger.error(message, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
