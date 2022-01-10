package com.compalinttracker.claimdb.printerServer;

import com.compalinttracker.claimdb.userProfile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrinterServerRepository extends JpaRepository<PrinterServer, Long> {

    @Query(
            value = "SELECT * " +
                    "FROM printer_server WHERE name = ?1",
            nativeQuery = true
    )
    Optional<PrinterServer> findByServerName(String name);

    @Query(
            value = "SELECT * " +
                    " FROM printer_server WHERE ip = ?1 " +
                    " AND port_number = ?2",
            nativeQuery = true
    )
    Optional<PrinterServer> findByIpAndPortNumber(String ip, int portNumber);


}
