package com.hhplusconcert.infra.waitingToken.impl;

import com.hhplusconcert.common.exception.model.CustomGlobalException;
import com.hhplusconcert.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.repository.WaitingTokenRepository;
import com.hhplusconcert.infra.waitingToken.orm.WaitingTokenJpoRepository;
import com.hhplusconcert.infra.waitingToken.orm.jpo.WaitingTokenJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WaitingTokenRepositoryImpl implements WaitingTokenRepository {
    //
    private final WaitingTokenJpoRepository waitingTokenJpoRepository;

    @Override
    public void save(WaitingToken waitingToken) {
        //
        this.waitingTokenJpoRepository.save(new WaitingTokenJpo(waitingToken));
    }

    @Override
    public WaitingToken findByIdWithThrow(String tokenId) {
        Optional<WaitingTokenJpo> jpo = this.waitingTokenJpoRepository.findById(tokenId);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.TOKEN_NOT_FOUND);
        return jpo.get().toDomain();
    }

    @Override
    public WaitingToken findByUserIdAndSeriesId(String userId, String seriesId) {
        //
        WaitingTokenJpo jpo = this.waitingTokenJpoRepository.findByUserIdAndSeriesId(userId, seriesId).orElseThrow();
        return jpo.toDomain();
    }

    @Override
    public void deleteById(String tokenId) {
        //
        this.waitingTokenJpoRepository.deleteById(tokenId);
    }

    @Override
    public void deleteAllByIds(List<String> tokenIds) {
        //
        this.waitingTokenJpoRepository.deleteAllById(tokenIds);
    }
}
