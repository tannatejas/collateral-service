package com.tt.collateralservice.restclient.dto;

import com.tt.collateralservice.dto.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionResponse {

    private String accountId;
    private List<Position> positions;

    @Override
    public String toString() {
        return "PositionResponse{" +
                "accountId='" + accountId + '\'' +
                ", positions=" + positions +
                '}';
    }
}
