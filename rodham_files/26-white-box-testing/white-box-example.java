

(01) float calculateInterestRate(Account account) {
(02) 
(03) 	final float BUSINESS_RATE	= 3.0f;
(04) 	final float INDIVIDUAL_RATE	= 1.0f;
(05) 	final float BONUS		= 0.5f;
(06) 	final float MAX_RATE		= 7.0f;
(07) 
(08) 	float rate = 0.0f;
(09) 	
(10) 	switch (account.Type()) {
(11) 		case BUSINESS:
(12) 			rate = BUSINESS_RATE;
(13) 			break;
(14) 
(15) 		case INDIVIDUAL:
(16) 			rate = INDIVIDUAL_RATE;
(17) 			if (account.getCustomer().isSeniorCitizen()) {
(18) 				rate += BONUS;
(19) 			}
(20) 			break;
(21) 
(22) 		default:
(23) 			// should be impossible
(24) 			assert(false);
(25) 			break;
(26) 	}
(27) 
(28) 	if (account.getYearsOpen() > 5) {
(29) 		rate += BONUS;
(30) 	}
(31) 
(32) 	if (account.getBalance() > 100000.0f) {
(33) 		rate += ((account.getBalance() / 50000.0f) * BONUS);
(34) 	}
(35) 	
(36) 	if (rate > MAX_RATE) {
(37) 		rate = MAX_RATE;
(38) 	}
(39) 
(40) 	return rate;
(41) }


DATA-FLOW TESTING
=================
Definitions of "rate": 08, 12, 16, 18, 29, 33, 37

Uses of "rate": 18, 29, 33, 36, 40

Use/Definition Pairs:
18: 16
29: 12, 16, 18
33: 12, 16, 18, 29
36: 12, 16, 18, 29, 33
40: 12, 16, 18, 29, 33, 37

Make sure you have at least one test case that tests each Use/Definition pair.
One test case might cover multiple Use/Definition pairs.


