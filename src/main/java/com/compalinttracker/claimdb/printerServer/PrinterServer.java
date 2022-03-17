package com.compalinttracker.claimdb.printerServer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "PrinterServer")
@Data
@NoArgsConstructor
@Table(
        name = "printer_server",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"ip", "port_number"})
        }
)
public class PrinterServer {

    @Id
    @SequenceGenerator(
            name = "printer_server_sequence",
            sequenceName = "printer_server_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "printer_server_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String name;

    @Column(
            nullable = false
    )
    private String ip;

    @Column(
            name = "port_number",
            nullable = false
    )
    private int portNumber;
}
