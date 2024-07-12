package com.hhplusconcert.domain.watingToken.repository;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;

import java.util.List;

public interface WaitingTokenRepository {
    //
    void save(WaitingToken waitingToken);
    WaitingToken findById(String tokenId);
    WaitingToken findByUserIdAndSeriesId(String userId, String seriesId);
    void deleteById(String tokenId);
    void deleteAllByIds(List<String> tokenIds);
}
