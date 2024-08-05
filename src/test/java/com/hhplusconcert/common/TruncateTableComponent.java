package com.hhplusconcert.common;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class TruncateTableComponent {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PlatformTransactionManager transactionManager;

    public void truncateTable(Runnable runnable, String... tableNames) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // Clear Redis queue
            if(runnable != null) {
                runnable.run();
            }

            //FIXME 고도화 필요 -> 사유: 특정 데이터 베이스에 종속적 !
            // Truncate table within the transaction
            for (String tableName : tableNames) {
                String sql = String.format("TRUNCATE TABLE %s", tableName);
                entityManager.createNativeQuery(sql).executeUpdate();
            }

            // Commit the transaction
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            // Rollback the transaction in case of an error
            transactionManager.rollback(status);
            throw e;
        }
    }
}
