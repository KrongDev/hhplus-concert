package com.hhplusconcert.domain.watingToken.repository;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;

import java.util.List;

public interface WaitingTokenRepository {
    //
    void save(WaitingToken waitingToken);
    WaitingToken findById(String tokenId);
    WaitingToken findByUserIdAndSeriesId(String userId, String seriesId);
    Long countTokens();
    List<WaitingToken> findAllByExpired(long expiredTime);
    void deleteByUserIdAndSeriesId(String userId, String seriesId);
    void deleteAllByIds(List<String> tokenIds);
    boolean existsByUserIdAndSeriesId(String userId, String seriesId);
}
