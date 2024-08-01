package com.hhplusconcert.domain.watingToken.service;

import com.hhplusconcert.common.dto.IdName;
import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.repository.WaitingTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WaitingTokenService {
    //
    private final WaitingTokenRepository waitingTokenRepository;

    @Value("${concert.limit.processCount:50}")
    private Long limitProcessCount;

    @Transactional
    public String create(String userId, String seriesId) {
        //
        if(waitingTokenRepository.existsByUserIdAndSeriesId(userId, seriesId))
            throw new CustomGlobalException(ErrorType.TOKEN_ALREADY_EXIST);
        WaitingToken token = WaitingToken.newInstance(userId, seriesId);
        this.waitingTokenRepository.save(token);
        return token.getTokenId();
    }

    public WaitingToken loadWaitingToken(String tokenId) {
        //
        WaitingToken token = this.waitingTokenRepository.findById(tokenId);
        if(Objects.isNull(token))
            throw new CustomGlobalException(ErrorType.TOKEN_NOT_FOUND);
        return token;
    }

    public WaitingToken loadWaitingToken(String userId, String seriesId) {
        //
        WaitingToken waitingToken = this.waitingTokenRepository.findByUserIdAndSeriesId(userId, seriesId);
        if(Objects.isNull(waitingToken))
            throw new CustomGlobalException(ErrorType.TOKEN_NOT_FOUND);
        return waitingToken;
    }

    public List<WaitingToken> loadWaitingTokensByExpired() {
        //
        return this.waitingTokenRepository.findAllByExpired(System.currentTimeMillis());
    }

    public Long addTokensCount() {
        //
        Long nowTokenCount = this.waitingTokenRepository.countTokens();
        return limitProcessCount - nowTokenCount;
    }

    public boolean alreadyToken(String userId, String seriesId) {
        //
        return this.waitingTokenRepository.existsByUserIdAndSeriesId(userId, seriesId);
    }

    @Transactional
    public void healthCheck(String tokenId) {
        //
        WaitingToken waitingQueue = this.loadWaitingToken(tokenId);
        waitingQueue.healthCheck();
        this.waitingTokenRepository.save(waitingQueue);
    }

    @Transactional
    public void processExpiredTokens() {
        List<WaitingToken> tokens = this.loadWaitingTokensByExpired();
        List<String> tokenIds = tokens.stream().map(WaitingToken::getTokenId).toList();
        this.deleteWaitingTokens(tokenIds);
    }

    @Transactional
    public void deleteByUserIdAndSeriesId(String userId, String seriesId) {
        //
        this.waitingTokenRepository.deleteByUserIdAndSeriesId(userId, seriesId);
    }

    @Transactional
    public void deleteWaitingTokens(List<String> tokenIds) {
        //
        this.waitingTokenRepository.deleteAllByIds(tokenIds);
    }
}
