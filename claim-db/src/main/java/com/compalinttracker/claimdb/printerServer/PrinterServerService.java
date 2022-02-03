package com.compalinttracker.claimdb.printerServer;

import java.util.Collection;

public interface PrinterServerService {

    Collection<PrinterServer> create(PrinterServer printerServer);
    Collection<PrinterServer> list(int limit, int page);
    PrinterServer get(long id);
    PrinterServer update(long id, PrinterServer printerServer);
    Boolean delete(long id);
    Boolean ping(long id);
}
