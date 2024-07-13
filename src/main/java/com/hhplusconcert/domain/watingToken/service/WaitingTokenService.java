package com.hhplusconcert.domain.watingToken.service;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.repository.WaitingTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitingTokenService {
    //
    private final WaitingTokenRepository waitingTokenRepository;

    @Transactional
    public String create(String userId, String seriesId) {
        //
        WaitingToken token = WaitingToken.newInstance(userId, seriesId);
        this.waitingTokenRepository.save(token);
        return token.getTokenId();
    }

    public WaitingToken loadWaitingToken(String tokenId) {
        //
        return this.waitingTokenRepository.findById(tokenId);
    }

    public WaitingToken loadWaitingToken(String userId, String seriesId) {
        //
        return this.waitingTokenRepository.findByUserIdAndSeriesId(userId, seriesId);
    }

    @Transactional
    public void deleteWaitingToken(String tokenId) {
        //
        this.waitingTokenRepository.deleteById(tokenId);
    }

    @Transactional
    public void deleteWaitingTokens(List<String> tokenIds) {
        //
        this.waitingTokenRepository.deleteAllByIds(tokenIds);
    }
}
