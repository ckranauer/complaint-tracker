package com.compalinttracker.claimdb.printerServer;

import java.util.Collection;

public interface PrinterServerService {

    PrinterServer create(PrinterServer printerServer);
    Collection<PrinterServer> list();
    PrinterServer get(long id);
    PrinterServer update(long id, PrinterServer printerServer);
    Boolean delete(long id);
    Boolean ping(long id);
}
