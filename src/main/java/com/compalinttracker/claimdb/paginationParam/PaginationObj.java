package com.compalinttracker.claimdb.paginationParam;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationObj {

    private int page;
    private int limit;
}
