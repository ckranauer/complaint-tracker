package com.compalinttracker.claimdb.printerServer;

import com.compalinttracker.claimdb.complaint.labelPrinter.ZebraPrinter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PrinterServerImpl implements PrinterServerService {

    private final PrinterServerRepository printerServerRepository;
    private final ZebraPrinter zebraPrinter;

    @Override
    public Collection<PrinterServer> create(PrinterServer printerServer) {
        log.info("Saving new server: {}", printerServer.getName());
        Optional<PrinterServer> printerServerOptional = printerServerRepository.findByServerName(printerServer.getName());
        // validate the server
        printerServerRepository.save(printerServer);
        return printerServerRepository.findAll();
    }

    @Override
    public Collection<PrinterServer> list(int limit, int page) {
        log.info("Fetching all servers");

        return printerServerRepository.findAll();
    }

    @Override
    public PrinterServer get(long id) {
        log.info("Fetching server by id: {}", id);
        return printerServerRepository.findById(id).get();
    }

    @Override
    public Collection<PrinterServer> update( ServerUpdateDto serverUpdateDto) {
        log.info("Update printer server: {}", serverUpdateDto.getId());

        Optional<PrinterServer> printerServerOptional = printerServerRepository.findById(serverUpdateDto.getId());
        if(printerServerOptional.isEmpty()){
            throw new IllegalStateException(String.format("Printer server with id: "+ serverUpdateDto.getId() + " is not exist."));
        }

        PrinterServer printerServer = printerServerOptional.get();
        // TODO: write IP, port number, name, description validator
        printerServer.setIp(serverUpdateDto.getIp());
        printerServer.setName(serverUpdateDto.getName());
        printerServer.setPortNumber(serverUpdateDto.getPortNumber());
        printerServerRepository.save(printerServer);

        return printerServerRepository.findAll();
    }

    @Override
    public Collection<PrinterServer> delete(long id) {
        printerServerRepository.deleteById(id);
        return printerServerRepository.findAll();
    }

    @Override
    public Boolean ping(long id) {
        Optional<PrinterServer> printerServerOptional = printerServerRepository.findById(id);

        PrinterServer server = printerServerOptional.get();
        try{
            zebraPrinter.startConnection(server.getIp(), server.getPortNumber());
        }catch(IOException exception){
            System.out.println("Connection does not succeed!");
            return Boolean.FALSE;
        }

        try{
            zebraPrinter.stopConnection();
        }catch(IOException exception){
            System.out.println("Close connection does not succeed!");
            return Boolean.FALSE;
        }


        return Boolean.TRUE;
    }
}
