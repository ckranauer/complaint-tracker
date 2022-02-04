package com.compalinttracker.claimdb.printerServer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerUpdateDto {

    private Long id;
    private String name;
    private String ip;
    private int portNumber;
}
