package com.compalinttracker.claimdb.printerServer;

import com.compalinttracker.claimdb.complaint.Complaint;
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

        // validate the server
        checkIfPrintServerAlreadyExists(printerServer);

        // check if IP address && port number is taken
        checkIfIpAndPortNumberIsTaken(printerServer);

        printerServerRepository.save(printerServer);
        return printerServerRepository.findAll();
    }

    private void checkIfIpAndPortNumberIsTaken(PrinterServer printerServer) {
        if (printerServerRepository.findByIpAndPortNumber(printerServer.getIp(), printerServer.getPortNumber()).isPresent()) {
            throw new IllegalStateException((String.format("Printer server with " + printerServer.getIp() + " IP address and "
                    + printerServer.getPortNumber() + " port number is already exist")));
        }
    }

    private void checkIfPrintServerAlreadyExists(PrinterServer printerServer) {
        Optional<PrinterServer> printerServerOptional = printerServerRepository.findByServerName(printerServer.getName());
        if (printerServerOptional.isPresent()) {
            throw new IllegalStateException((String.format("Printer server with " + printerServer.getName() + " name is already exist")));
        }
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
    public Collection<PrinterServer> update(ServerUpdateDto serverUpdateDto) {
        log.info("Update printer server: {}", serverUpdateDto.getId());

        Optional<PrinterServer> printerServerOptional = printerServerRepository.findById(serverUpdateDto.getId());
        isServerExists(serverUpdateDto, printerServerOptional);

        throwExceptionIfNameIsTaken(serverUpdateDto);

        throwExceptionIfIpAndPortIsTaken(serverUpdateDto);

        PrinterServer printerServer = getPrinterServer(serverUpdateDto, printerServerOptional);
        printerServerRepository.save(printerServer);
        return printerServerRepository.findAll();
    }

    private PrinterServer getPrinterServer(ServerUpdateDto serverUpdateDto, Optional<PrinterServer> printerServerOptional) {
        PrinterServer printerServer = printerServerOptional.get();
        printerServer.setIp(serverUpdateDto.getIp());
        printerServer.setName(serverUpdateDto.getName());
        printerServer.setPortNumber(serverUpdateDto.getPortNumber());
        return printerServer;
    }

    private void throwExceptionIfIpAndPortIsTaken(ServerUpdateDto serverUpdateDto) {
        Optional<PrinterServer> printerServerOptional = printerServerRepository.findByIpAndPortNumber(serverUpdateDto.getIp(), serverUpdateDto.getPortNumber());
        if (printerServerOptional.isPresent()) {
            if (printerServerOptional.get().getId() != serverUpdateDto.getId()) {
                throw new IllegalStateException((String.format("Printer server with " + serverUpdateDto.getIp() + " IP address and "
                        + serverUpdateDto.getPortNumber() + " port number is taken")));
            }
        }
    }

    private void throwExceptionIfNameIsTaken(ServerUpdateDto serverUpdateDto) {
        Optional<PrinterServer> printerServerOptional = printerServerRepository.findByServerName(serverUpdateDto.getName());
        if (printerServerOptional.isPresent()) {
            if (serverUpdateDto.getId() != printerServerOptional.get().getId()) {
                throw new IllegalStateException(String.format("Printer server with name: " + serverUpdateDto.getName() + " is taken."));
            }
        }
    }

    private void isServerExists(ServerUpdateDto serverUpdateDto, Optional<PrinterServer> printerServerOptional) {
        if (printerServerOptional.isEmpty()) {
            throw new IllegalStateException(String.format("Printer server with id: " + serverUpdateDto.getId() + " is not exist."));
        }
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
        try {
            zebraPrinter.startConnection(server.getIp(), server.getPortNumber());
        } catch (IOException exception) {
            System.out.println("Connection does not succeed!");
            return Boolean.FALSE;
        }

        try {
            zebraPrinter.stopConnection();
        } catch (IOException exception) {
            System.out.println("Close connection does not succeed!");
            return Boolean.FALSE;
        }


        return Boolean.TRUE;
    }
}
