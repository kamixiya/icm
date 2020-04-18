package com.kamixiya.icm.persistence.content.repository.indexLibrary;

import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;

/**
 * IndexLibraryRepository
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Repository
public interface IndexLibraryRepository extends JpaRepository<IndexLibrary, Long>, JpaSpecificationExecutor<IndexLibrary> {

    /**
     * 更新指标库数据
     *
     * @param reducePassageAmount 变化后的预算调减在途金额
     * @param allocationAmount 变化后的分配金额
     * @param occupationAmount 变化前后的占用金额差值     变化后-变化前
     * @param passageAmount    在途金额变化前后的差值
     * @param adjustmentAmount 调整金额变化前后的差值
     * @param ids              id数组，数组中的id变化金额必须一致，否则只传单个id
     */
    @Modifying(flushAutomatically = true)
    @Query(value = "update IndexLibrary set allocationAmount = allocationAmount + :allocationAmount, reducePassageAmount = reducePassageAmount + :reducePassageAmount, occupationAmount = occupationAmount + :occupationAmount, passageAmount = passageAmount + :passageAmount, " +
            "adjustmentAmount = adjustmentAmount + :adjustmentAmount, availableAmount = allocationAmount + adjustmentAmount - passageAmount - occupationAmount - reducePassageAmount where id in (:ids)")
    void updateAmount(@Param("reducePassageAmount") Double reducePassageAmount, @Param("allocationAmount") Double allocationAmount, @Param("occupationAmount") Double occupationAmount, @Param("passageAmount") Double passageAmount, @Param("adjustmentAmount") Double adjustmentAmount, @NotEmpty @Param("ids") Long... ids);

}
