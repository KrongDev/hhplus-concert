package com.hhplusconcert.interfaces.interceptor;


import com.hhplusconcert.application.waitingQueue.facade.WaitingQueueSeekFacade;
import com.hhplusconcert.application.waitingToken.facade.WaitingTokenSeekFacade;
import com.hhplusconcert.common.annotation.QueueCheckAnnotation;
import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueTokenCheckInterceptor implements HandlerInterceptor {

    private final WaitingTokenSeekFacade waitingTokenSeekFacade;
    private final WaitingQueueSeekFacade waitingQueueSeekFacade;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        QueueCheckAnnotation queueCheckAnnotation = ((HandlerMethod) handler).getMethodAnnotation(QueueCheckAnnotation.class);
        if(queueCheckAnnotation == null){
            return true;
        }

        String tokenId = request.getHeader("tokenId");
        WaitingToken token = this.waitingTokenSeekFacade.loadWaitingToken(tokenId);
        token.validateExpired();
        WaitingQueue queue = this.waitingQueueSeekFacade.loadNowWaitingQueue(tokenId);

        if(!queue.isProcess())
            throw new CustomGlobalException(ErrorType.WAITING_QUEUE_IS_NOT_PROCESSING);
        return true;
    }
}
