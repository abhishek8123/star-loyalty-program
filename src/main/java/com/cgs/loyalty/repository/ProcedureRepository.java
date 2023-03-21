package com.cgs.loyalty.repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cgs.loyalty.exception.CustomerNotExistException;
import com.cgs.loyalty.exception.PointsNotAvailableException;
import com.cgs.loyalty.util.CreditAmount;
import com.cgs.loyalty.util.DebitAmount;
import com.cgs.loyalty.util.UsePoints;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ProcedureRepository {

	@Autowired
	private EntityManager entityManager;
	
	
	// constants
	private static final String CREDIT_PROC_LOYALTY = "CREDITPROCEDURE";
	private static final String DEBIT_PROC_LOYALTY = "DEBITCUSTOMERPROCEDURE";
	private static final String USE_POINTS_PROC_LOYALTY = "USEPOINTSPROCEDURE";
	private static final String POINTSTO_AMMOUNT_PROC_LOYALTY = "USEPOINTSTOMONEY";


	// credit amount procedure call
	public void creditAmount(CreditAmount creditAmount) {

		log.info("credit procedure started");

		try {
			StoredProcedureQuery proc = entityManager.createStoredProcedureQuery(CREDIT_PROC_LOYALTY);
			proc.registerStoredProcedureParameter("v_account_id", String.class, ParameterMode.IN);
			proc.registerStoredProcedureParameter("v_credit_amount", String.class, ParameterMode.IN);
			proc.registerStoredProcedureParameter("v_beneficiary", String.class, ParameterMode.IN);
			proc.registerStoredProcedureParameter("v_beneficiary_acc_no", String.class, ParameterMode.IN);

			proc.registerStoredProcedureParameter("v_total_amount", String.class, ParameterMode.OUT);
			proc.registerStoredProcedureParameter("v_crdit_ammount2", String.class, ParameterMode.OUT);
			proc.registerStoredProcedureParameter("v_account_id2", String.class, ParameterMode.OUT);

			proc.setParameter("v_account_id", creditAmount.getAccountId());
			proc.setParameter("v_credit_amount", creditAmount.getCreditAmount());
			proc.setParameter("v_beneficiary", creditAmount.getBeneficiary());
			proc.setParameter("v_beneficiary_acc_no", creditAmount.getBeneficiary_acc_no());

			proc.execute();

			log.info("Total amount after crediting :" + proc.getOutputParameterValue("v_total_amount"));
			log.info("credited amount :" + proc.getOutputParameterValue("v_crdit_ammount2"));
			log.info("account id :" + proc.getOutputParameterValue("v_account_id2"));
		} catch (Exception e) {
			log.error("Exception while executing stored procedure :: " + e.getMessage());
			throw new CustomerNotExistException();
		}

		log.info("credit procedure Ended");
	}

	// debit amount procedure call
	public void debitAmount(DebitAmount debitAmount) {

		log.info("customer debit procedure started");

		try {
			StoredProcedureQuery proc = entityManager.createStoredProcedureQuery(DEBIT_PROC_LOYALTY);
			proc.registerStoredProcedureParameter("v_account_id", String.class, ParameterMode.IN);
			proc.registerStoredProcedureParameter("v_debit_amount", String.class, ParameterMode.IN);
			proc.registerStoredProcedureParameter("v_beneficiary", String.class, ParameterMode.IN);
			proc.registerStoredProcedureParameter("v_beneficiary_acc_no", String.class, ParameterMode.IN);

			proc.registerStoredProcedureParameter("v_total_amount", String.class, ParameterMode.OUT);
			proc.registerStoredProcedureParameter("v_debit_ammount2", String.class, ParameterMode.OUT);
			proc.registerStoredProcedureParameter("v_account_id2", String.class, ParameterMode.OUT);
			proc.registerStoredProcedureParameter("v_points_per_trans", String.class, ParameterMode.OUT);

			proc.setParameter("v_account_id", debitAmount.getAccountId());
			proc.setParameter("v_debit_amount", debitAmount.getDebitAmount());
			proc.setParameter("v_beneficiary", debitAmount.getBeneficiary());
			proc.setParameter("v_beneficiary_acc_no", debitAmount.getBeneficiary_acc_no());

			proc.execute();

			log.info("Total amount after debiting" + proc.getOutputParameterValue("v_total_amount"));
			log.info("debit amount" + proc.getOutputParameterValue("v_debit_ammount2"));
			log.info("account id" + proc.getOutputParameterValue("v_account_id2"));
			log.info("points per transaction" + proc.getOutputParameterValue("v_points_per_trans"));
		} catch (Exception e) {
			log.error("Exception while executing stored procedure :: " + e.getMessage());
			throw new CustomerNotExistException();
		}

		log.info("debit procedure Ended");
	}

	// procedure call for use points
	public void usePoints(UsePoints usePoints) {

		log.info("usepoints procedure started");

		try {
			StoredProcedureQuery proc = entityManager.createStoredProcedureQuery(USE_POINTS_PROC_LOYALTY);
			proc.registerStoredProcedureParameter("v_account_id", String.class, ParameterMode.IN);
			proc.registerStoredProcedureParameter("v_points", String.class, ParameterMode.IN);

			proc.registerStoredProcedureParameter("v_points_used", String.class, ParameterMode.OUT);

			proc.setParameter("v_account_id", usePoints.getAccountId());
			proc.setParameter("v_points", usePoints.getPoints());

			proc.execute();

			log.info("used points" + proc.getOutputParameterValue("v_points_used"));
		} catch (Exception e) {
			log.error("Exception while executing stored procedure :: " + e.getMessage());
			throw new PointsNotAvailableException();
		}

		log.info("usepoints procedure Ended");

	}

	// procedure call for use points and convert to money
	public String usePointstoMoney(UsePoints usePoints) {

		log.info("usepoints to convert money procedure started");

		try {
			StoredProcedureQuery proc = entityManager.createStoredProcedureQuery(POINTSTO_AMMOUNT_PROC_LOYALTY);
			proc.registerStoredProcedureParameter("v_account_id", String.class, ParameterMode.IN);
			proc.registerStoredProcedureParameter("v_points", String.class, ParameterMode.IN);

			proc.registerStoredProcedureParameter("v_points_used", String.class, ParameterMode.OUT);
			proc.registerStoredProcedureParameter("v_amount_u_earn", String.class, ParameterMode.OUT);

			proc.setParameter("v_account_id", usePoints.getAccountId());
			proc.setParameter("v_points", usePoints.getPoints());

			proc.execute();
			
			log.info("used points" + proc.getOutputParameterValue("v_points_used"));
			log.info("amount get" + proc.getOutputParameterValue("v_amount_u_earn"));
			
			
			log.info("usepoints to convert money procedure Ended");
			
			return (String) proc.getOutputParameterValue("v_amount_u_earn");
			
			
		} catch (Exception e) {
			log.error("Exception while executing stored procedure :: " + e.getMessage());
			throw new PointsNotAvailableException();
		}
		
	}

}
