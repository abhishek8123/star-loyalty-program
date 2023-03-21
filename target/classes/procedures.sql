---------------------
1) DEBIT PROCEDURE
---------------------

create or replace PROCEDURE DEBITCUSTOMERPROCEDURE(
v_account_id IN VARCHAR2,
v_debit_amount IN VARCHAR2,
v_beneficiary IN VARCHAR2,
v_beneficiary_acc_no IN VARCHAR2,

v_total_amount OUT VARCHAR2,
v_debit_ammount2 OUT VARCHAR2,
v_account_id2 OUT VARCHAR2,
v_points_per_trans OUT VARCHAR2

) AS 
va_account_id VARCHAR2(225);
va_account_balance VARCHAR2(225);
va_total_points VARCHAR2(225);
va_points_per_transaction VARCHAR2(225);
va_bank_name VARCHAR2(225);
FLDERR EXCEPTION;

BEGIN
    va_bank_name:= 'AZIZI';
     
    SELECT ACCOUNT_ID, ACCOUT_BALANCE, TOTAL_POINTS
    INTO va_account_id, va_account_balance, va_total_points 
    FROM CUSTOMER_ACCOUNT 
    WHERE ACCOUNT_ID = v_account_id;
    
    IF va_account_id = v_account_id THEN
      DBMS_OUTPUT.PUT_LINE('ID EXIST IN DATA BASE');
     
      SELECT POINTS_PER_TRANS, BANK_NAME
      INTO va_points_per_transaction, va_bank_name
      FROM CUSTOMER_POINTS_VALUE
      WHERE BANK_NAME = va_bank_name;
      DBMS_OUTPUT.PUT_LINE(va_points_per_transaction);
      
      v_total_amount := va_account_balance - v_debit_amount;
      va_points_per_transaction := va_points_per_transaction * v_debit_amount;
      va_total_points := va_total_points + va_points_per_transaction;
      v_debit_ammount2:=v_debit_amount;
      DBMS_OUTPUT.PUT_LINE(v_total_amount);
      DBMS_OUTPUT.PUT_LINE(v_debit_ammount2);
      DBMS_OUTPUT.PUT_LINE(va_points_per_transaction);
      DBMS_OUTPUT.PUT_LINE(va_total_points);
            
        UPDATE CUSTOMER_ACCOUNT 
        SET ACCOUT_BALANCE = v_total_amount , CREATED_AT = sysdate, TOTAL_POINTS = va_total_points
        WHERE ACCOUNT_ID = v_account_id;
        
         -- "STARMPAY"."CUSTOMER_TRANSACTION_SEQ".nextval  (auto increment in transaction table)
        INSERT INTO CUSTOMER_TRANSACTION ( ACCOUNT_ID, BENEFICIARY, BENEFICIARY_ACC_NO, CREATED_AT, CREDI_AMOUNT, DEBIT_AMOUNT, POINTS_FOR_CREDIT, TOTAL_POINTS, TOTAL_AMOUNT ) 
                                   VALUES( v_account_id, v_beneficiary, v_beneficiary_acc_no, sysdate, 0, v_debit_ammount2 , va_points_per_transaction, va_total_points, v_total_amount );


        v_total_amount := v_total_amount;
        v_debit_ammount2 := v_debit_ammount2;
        v_account_id2 := v_account_id;
        v_points_per_trans := va_points_per_transaction;
    
    ELSE 
       raise FLDERR;
    END IF;  
    commit;
END DEBITCUSTOMERPROCEDURE;

---------------------
2) CREDITPROCEDURE
---------------------

create or replace PROCEDURE CREDITPROCEDURE(
v_account_id IN VARCHAR2,
v_credit_amount IN VARCHAR2,
v_beneficiary IN VARCHAR2,
v_beneficiary_acc_no IN VARCHAR2,

v_account_id2 OUT VARCHAR2,
v_crdit_ammount2 OUT VARCHAR2,
v_total_amount OUT VARCHAR2


) AS 
v_account_id3 VARCHAR2(225);
v_account_balance VARCHAR2(225);
FLDERR EXCEPTION;


BEGIN
    SELECT ACCOUNT_ID, ACCOUT_BALANCE
    INTO v_account_id3, v_account_balance 
    FROM CUSTOMER_ACCOUNT 
    WHERE ACCOUNT_ID = v_account_id;
    
    IF v_account_id3 = v_account_id THEN
      DBMS_OUTPUT.PUT_LINE('ID EXIST IN DATA BASE');
     -- v_account_id2 := v_account_id;
      v_total_amount := v_account_balance + v_credit_amount;
      v_crdit_ammount2 := v_credit_amount;
       DBMS_OUTPUT.PUT_LINE(v_total_amount);
       
      update CUSTOMER_ACCOUNT 
      SET ACCOUT_BALANCE = v_total_amount , CREATED_AT = sysdate
      WHERE ACCOUNT_ID = v_account_id;
      -- "STARMPAY"."CUSTOMER_TRANSACTION_SEQ".nextval  (auto increment in transaction table)
      INSERT INTO CUSTOMER_TRANSACTION ( ACCOUNT_ID, BENEFICIARY, BENEFICIARY_ACC_NO, CREATED_AT, CREDI_AMOUNT, DEBIT_AMOUNT, POINTS_FOR_CREDIT, TOTAL_POINTS, TOTAL_AMOUNT ) 
                              VALUES( v_account_id3, v_beneficiary, v_beneficiary_acc_no, sysdate, v_crdit_ammount2, 0 , 0, 0, v_total_amount );
  
    else 
      raise FLDERR;
    END IF;  
    
    commit;
    
END CREDITPROCEDURE;

----------------
3) USE POINTS
----------------

create or replace PROCEDURE USEPOINTSPROCEDURE(

v_account_id IN VARCHAR2,
v_points IN VARCHAR2,

v_points_used OUT VARCHAR2

) AS 
va_total_points VARCHAR(225);
va_remaining_points VARCHAR2(225);
FLDERR EXCEPTION;

BEGIN
  SELECT TOTAL_POINTS  
  INTO va_total_points 
  FROM CUSTOMER_ACCOUNT
  WHERE ACCOUNT_ID = v_account_id;
  
  va_remaining_points := va_total_points - v_points;
     DBMS_OUTPUT.PUT_LINE(va_remaining_points);
     
  IF va_remaining_points >= 50 THEN
    DBMS_OUTPUT.PUT_LINE('POINTS ARE NOT IN NEGATIVE O THIS CONDITION');
      DBMS_OUTPUT.PUT_LINE(va_total_points);
     
        UPDATE CUSTOMER_ACCOUNT 
        SET CREATED_AT = sysdate, TOTAL_POINTS = va_remaining_points
        WHERE ACCOUNT_ID = v_account_id;
        
        v_points_used := v_points;
  ELSE
   raise FLDERR;
  END IF;

END USEPOINTSPROCEDURE;


-------------------------------
4)  POINTS TO MONEY PROCEDURE
-------------------------------

create or replace PROCEDURE USEPOINTSTOMONEY (
v_account_id IN VARCHAR2,
v_points IN VARCHAR2,

v_points_used OUT VARCHAR2,
v_amount_u_earn OUT VARCHAR2

) AS 
va_total_points VARCHAR(225);
va_remaining_points VARCHAR2(225);
va_account_balance VARCHAR2(225);
va_amount_for_points VARCHAR2(255);
va_ammount VARCHAR2(255);
va_per_points VARCHAR2(255);
va_bank_name VARCHAR2(255);
va_new_balance VARCHAR2(255);
FLDERR EXCEPTION;

BEGIN
  va_bank_name := 'AZIZI';
  v_points_used := v_points;

  SELECT TOTAL_POINTS , ACCOUT_BALANCE 
  INTO va_total_points , va_account_balance
  FROM CUSTOMER_ACCOUNT
  WHERE ACCOUNT_ID = v_account_id;
  
  SELECT PER_POINTS, AMMOUNT
  INTO va_per_points, va_ammount
  FROM CUSTOMER_POINTS_VALUE
  WHERE BANK_NAME = va_bank_name;
   
  va_remaining_points := va_total_points - v_points;
     DBMS_OUTPUT.PUT_LINE(v_points);
     DBMS_OUTPUT.PUT_LINE(va_remaining_points);
     
  va_amount_for_points := ((v_points)/(va_per_points))*va_ammount;
     DBMS_OUTPUT.PUT_LINE(va_amount_for_points);
     
  va_new_balance := va_account_balance + va_amount_for_points;   
     
     
  IF va_remaining_points >= 50 THEN
      DBMS_OUTPUT.PUT_LINE('POINTS ARE NOT IN NEGATIVE AND ZERO IN THIS CONDITION');
      DBMS_OUTPUT.PUT_LINE(va_total_points);
     
        UPDATE CUSTOMER_ACCOUNT 
        SET CREATED_AT = sysdate, TOTAL_POINTS = va_remaining_points, ACCOUT_BALANCE = va_new_balance
        WHERE ACCOUNT_ID = v_account_id;
        
         INSERT INTO CUSTOMER_TRANSACTION ( ACCOUNT_ID, BENEFICIARY, BENEFICIARY_ACC_NO, CREATED_AT, CREDI_AMOUNT, DEBIT_AMOUNT, POINTS_FOR_CREDIT, TOTAL_POINTS, TOTAL_AMOUNT ) 
         VALUES( v_account_id, 'FROM POINTS', '-', sysdate, va_amount_for_points, 0 , 0, va_remaining_points, va_new_balance); 
        
         v_amount_u_earn := va_amount_for_points;
  ELSE
   raise FLDERR;
  END IF;

END USEPOINTSTOMONEY;