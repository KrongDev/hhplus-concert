package com.hhplusconcert.domain.watingToken.repository;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;

import java.util.List;

public interface WaitingTokenRepository {
    //
    void save(WaitingToken waitingToken);
    WaitingToken findByIdWithThrow(String tokenId);
    WaitingToken findByUserIdAndSeriesId(String userId, String seriesId);
    List<WaitingToken> findAllByExpired(long expiredTime);
    void deleteById(String tokenId);
    void deleteAllByIds(List<String> tokenIds);
    boolean existsByUserIdAndSeriesId(String userId, String seriesId);
}
