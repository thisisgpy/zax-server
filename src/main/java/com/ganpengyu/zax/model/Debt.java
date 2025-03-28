package com.ganpengyu.zax.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 还款周期
 */
enum RepaymentPeriod {
    /**
     * 月付
     */
    Monthly(1),
    /**
     * 季付
     */
    Quarterly(3),
    /**
     * 半年付
     */
    SemiAnnually(6),
    /**
     * 年付
     */
    Annually(12),
    /**
     * 其他
     */
    Other(0);

    // 还款间隔月份
    private final int period;

    RepaymentPeriod(int period) {
        this.period = period;
    }

    public int getPeriod() {
        if (this == Other) {
            throw new IllegalArgumentException("不可计算的还款周期");
        }
        return period;
    }
}

/**
 * 还款计划
 */
@Data
class RepaymentPlan {

    // 还款日期
    private LocalDate repaymentDate;

    // 还款本金
    private BigDecimal principal;

    // 还款利息
    private BigDecimal interest;

    public RepaymentPlan(LocalDate repaymentDate, BigDecimal principal, BigDecimal interest) {
        this.repaymentDate = repaymentDate;
        this.principal = principal;
        this.interest = interest;
    }

    public RepaymentPlan(LocalDate repaymentDate) {
        this.repaymentDate = repaymentDate;
    }
}

/**
 * 债务
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/28
 */
@Data
public class Debt {

    // 实际到账金额
    private BigDecimal actualAmountReceived;

    // 是否采用浮动利率
    private boolean enableLPR;

    // 固定利率
    private BigDecimal fixedInterestRate;

    // 报价利率（基准利率）
    private BigDecimal loanPrimeRate;

    // 利率基点
    private BigDecimal basisPoint;

    // 还款周期
    private RepaymentPeriod repaymentPeriod;

    //首次还款日
    private LocalDate firstPaymentDate;

    // 最后还款日
    private LocalDate finalPaymentDate;

    /**
     * 生成先息后本还款计划明细
     *
     * @return {@link RepaymentPlan} 还款计划明细列表
     */
    private List<RepaymentPlan> genRepaymentPlanForInterestFirst() {
        // 非固定还款周期的债务，不能计算还款计划
        if (repaymentPeriod == RepaymentPeriod.Other) {
            return new ArrayList<>();
        }
        // 计算还款日
        List<RepaymentPlan> repaymentPlans = getRepaymentDays();
        // 计算执行利率
        BigDecimal appliedInterestRate = fixedInterestRate;
        if (enableLPR) {
            appliedInterestRate = loanPrimeRate.add(basisPoint);
        }
        // 总利息
        BigDecimal totalInterest = actualAmountReceived.multiply(appliedInterestRate);
        // 每次还款利息
        BigDecimal interestPaymentPerPeriod = totalInterest.divide(new BigDecimal(repaymentPlans.size()), 2, RoundingMode.HALF_UP);
        // 构造还款计划
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            repaymentPlan.setPrincipal(new BigDecimal("0.00"));
            repaymentPlan.setInterest(interestPaymentPerPeriod);
        }
        // 最后一次还款需要一次性偿还本金
        repaymentPlans.getLast().setInterest(new BigDecimal("0.00"));
        repaymentPlans.getLast().setPrincipal(actualAmountReceived);
        return repaymentPlans;
    }

    /**
     * 计算全部还款日期并生成还款计划
     *
     * @return {@link RepaymentPlan} 仅包含还款日期的还款计划列表
     */
    private List<RepaymentPlan> getRepaymentDays() {
        List<RepaymentPlan> repaymentPlanDays = new ArrayList<>();
        // 下次还款日
        LocalDate nextRepaymentDay = firstPaymentDate;
        do {
            RepaymentPlan repaymentPlan = new RepaymentPlan(nextRepaymentDay);
            repaymentPlanDays.add(repaymentPlan);
            nextRepaymentDay = nextRepaymentDay.plusMonths(repaymentPeriod.getPeriod());
            if (nextRepaymentDay.getMonthValue() == finalPaymentDate.getMonthValue()) {
                break;
            }
        } while (nextRepaymentDay.isBefore(finalPaymentDate) || nextRepaymentDay.isEqual(finalPaymentDate));
        // 最后还款日
        repaymentPlanDays.add(new RepaymentPlan(finalPaymentDate));
        return repaymentPlanDays;
    }

    public static void main(String[] args) {
        Debt debt = new Debt();
        debt.setActualAmountReceived(new BigDecimal("1200.00"));
//        debt.setEnableLPR(false);
//        debt.setFixedInterestRate(new BigDecimal("0.05"));
        debt.setEnableLPR(true);
        debt.setLoanPrimeRate(new BigDecimal("0.03"));
        debt.setBasisPoint(new BigDecimal("0.02"));
        debt.setFirstPaymentDate(LocalDate.of(2024, 11, 30));
        debt.setFinalPaymentDate(LocalDate.of(2025, 11, 30));
        debt.setRepaymentPeriod(RepaymentPeriod.Quarterly);
        List<RepaymentPlan> repaymentPlans = debt.genRepaymentPlanForInterestFirst();
        repaymentPlans.forEach(System.out::println);
    }

}
