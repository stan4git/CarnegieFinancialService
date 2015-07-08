package action;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import model.Account;
import model.Fund;
import model.FundPriceHistory;
import model.Position;
import model.Transaction;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.opensymphony.xwork2.ActionContext;

import util.CheckBadChar;
import util.DateUtil;
import util.FormatUtil;
import vo.FundVO;

public class TransitionDay extends BaseAction {

	private static final long serialVersionUID = 3339569870142714000L;
	private static final double AMOUNTUPBOUND = 1000000000.0D;
	private int userId;
	private User user;
	private String userName;
	private String passWord;
	private int role;
	private List<Fund> fundlist;
	private List<FundVO> fundVolist;
	private String newDate;
	private String feedBack1;
	private String feedBack2;
	private String transactionDate;
	private List<Transaction> transactions;

	public String init() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();
		
		try {
			String userObject = ActionContext.getContext().getSession()
					.get("userId").toString();
			if (userObject == null || userObject == "") {
				throw new Exception();
			}
			setUserId(Integer.parseInt(userObject));
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "login";
		}
		
		
		try {
			fundlist = session.selectList("selectAllFund");
			List<FundVO> fundVolistTemp = new ArrayList<FundVO>();
			if (fundlist.size() > 0) {
				Fund fundtemp = fundlist.get(0);
				Date transactionDateTemp = fundtemp.getLastTradingDate();
				transactionDate = DateUtil.dateToString(transactionDateTemp);
				newDate = DateUtil.getSpecifiedDayAfter(transactionDate);
				for (Fund fund : fundlist) {
					FundVO fundVo = new FundVO();
					fundVo.setFundId(fund.getFundId());
					fundVo.setFundName(fund.getFundName());
					fundVo.setTicker(fund.getTicker());
					fundVo.setFundValue(FormatUtil.parseValue(new Double(fund
							.getFundValue() / 100.0)));
					fundVolistTemp.add(fundVo);
				}
				fundVolist = fundVolistTemp;
			}
		} finally {
			session.close();
		}
		return "initSuccess";
	}

	public String executeTransiting() throws IOException, ParseException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();
		List<Transaction> translist = new ArrayList<Transaction>();
		try {
			// Step1: Judge is the user has login the system
			try {
				String userObject = ActionContext.getContext().getSession()
						.get("userId").toString();
				if (userObject == null || userObject == "")
					throw new Exception();
				setUserId(Integer.parseInt(userObject));
				user = session.selectOne("selectuserbyuserid", userId);
				if (user == null || user.getRole() == 0)
					throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
				return "login";
			}

			feedBack1 = "";
			// Step2: Judge are all the funds in the db have been setted the new value
			List<Fund> funds = session.selectList("selectAllFund");
			HashSet<String> fundSets = new HashSet<String>();
			if (funds.size() > 0) {
				for (Fund fund : funds) {
					fundSets.add(fund.getFundName());
				}
			}
			// validation of all input
			if (fundVolist.size() > 0) {
				for (FundVO fundVo : fundVolist) {
					String newPriceString = fundVo.getNewPrice();
					if (newPriceString == null || newPriceString.equals("")) {
						feedBack1 += fundVo.getFundName()+ "'s price must be setted! <br>";
					}
					if (fundSets.contains(fundVo.getFundName())) {
						fundSets.remove(fundVo.getFundName());
					}
				}
				if (fundSets.size() > 0) {
					for (String fundNameForshow : fundSets) {
						feedBack1 += "Fund Name is:"+ fundNameForshow+ "is the new Fund, it should be setted the price!<br>";
					}
				}
				if (!feedBack1.equals("")) {
					return "failure";
				}
			}

			// step3:Judge is there other one on transition Function
			List<Fund> currentFunds = session.selectList("selectAllFundForTD");
			if (currentFunds.size() <= 0) {
				feedBack1 += "System is busy, please try later!";
				return "failure";
			}

			Date maxDate;
			Date newDateView;

			// step4: judge is the date bigger than current biggest date
			maxDate = session.selectOne("selectCurrentMaxTime");
			newDateView = DateUtil.stringToDate(CheckBadChar
					.fixBadChars(newDate));
			if (newDateView.compareTo(maxDate) <= 0) {
				feedBack1 = "This Day has been setted or your input date "
						+ "must be greater than the last tranding day of the database!";
				return "failure";
			}

			// step5: Lock 4 tables(related records): Fund, Position,Transaction,Account
			// Begin the big Transaction
			translist = session.selectList("selectAllPendingTransactions");
			// Condition1: There are some pending transactions
			if (translist.size() > 0) {
				try {
					this.LockFourTables(session, translist);

					// step6: Handle all the pending transaction, referring to
					// update/insert 5 tables
					if (fundVolist.size() > 0) {
						for (FundVO fundVo : fundVolist) {
							String fundName = fundVo.getFundName();
							double newPriceDouble = Double.parseDouble(fundVo.getNewPrice());
							newPriceDouble = FormatUtil.getdouble(newPriceDouble);
							long newPrice = (long) (newPriceDouble * 100);
							Fund fund = session.selectOne("selectfundbyfundname", fundName);
							int fundId = fund.getFundId();

							// step1: update the Fund Table
							fund.setFundValue(newPrice);
							fund.setLastTradingDate(newDateView);
							session.update("updateFund", fund);

							// step2: insert one record to the FundPriceHistory
							FundPriceHistory fph = new FundPriceHistory();
							fph.setFundId(fundId);
							fph.setPrice(newPrice);
							fph.setPriceDate(newDateView);
							session.insert("insertFundPriceHistory", fph);

							// step3: retrieve all the records from transaction
							// table (Buy & Sell Pending)
							transactions = new ArrayList<Transaction>();
							transactions = session.selectList("selectTransactionsByFundIdForLock",fundId);

							if (transactions.size() > 0) {
								for (Transaction trans : transactions) {
									int transType = trans.getTransactionType();// 0-buy fund, 1-sell fund
									long amountL = trans.getAmount();
									double amount = amountL / 100.00d;
									int accountId = trans.getAccountId();
									int userId = trans.getUserId();
									long sellShares = trans.getShares();
									// buy
									if (transType == 0) {
										double sharesD = amount/ newPriceDouble;
										// lock account table
										double currentShares = 0.0d;
										Account account = session.selectOne("selectaccountbyaccountidForLock",accountId);
										// lock position table
										Position searchCondition = new Position();
										searchCondition.setFundId(fundId);
										searchCondition.setUserId(userId);
										Position position = session.selectOne("selectPositionByConditionForLock",searchCondition);
										if (position != null) {
											currentShares = new Double(position.getShares() / 100.00);
										}
										// 1.1 Underflow lower than 0.001
										if (sharesD < 0.001) {
											// update Transaction Table
											trans.setExecuteDate(newDateView);
											trans.setStatus(2);
											trans.setErrorMessage("The Price of the fund which you bought"
													+ " is much bigger than your Amount,this transaction has been cancelled!");
											session.update("updateTransForTD",trans);
											// 2)update the Account Table
											account.setFrozenBalance(account.getFrozenBalance()- amountL);
											account.setAvailBalance(account.getAvailBalance()+ amountL);
											account.setLastTradingDay(newDateView);
											session.update("updateAccount",account);
											continue;
										}

										// 1.2 Over the Maximum of allowed
										// Shares
										// The Maximum of shares is
										// 100000000.000 in the database
										else if (sharesD > 100000000.000|| currentShares + sharesD > 100000000.000) {
											// update Transaction Table
											trans.setExecuteDate(newDateView);
											trans.setStatus(2);
											trans.setErrorMessage("You cannot possess a fund's shares more than"
													+ " 100,000,000.000, this transaction has been cancelled!");
											session.update("updateTransForTD",trans);
											// 2)update the Account Table
											account.setFrozenBalance(account.getFrozenBalance()- amountL);
											account.setAvailBalance(account.getAvailBalance()+ amountL);
											account.setLastTradingDay(newDateView);
											session.update("updateAccount",account);
											continue;
										}

										// 2.Overflow 1billion
										// Single Transaction amount's maximum
										// is one billion
										else if (amount > AMOUNTUPBOUND) {
											// update Transaction Table
											trans.setStatus(2);
											trans.setExecuteDate(newDateView);
											trans.setErrorMessage("The Single Tranaction Amount is not allowed more than $1,000,000,000 "
													+ "and the transaction has been canceled!");
											session.update("updateTransForTD",trans);
											// 2)update the Account Table
											account.setFrozenBalance(account.getFrozenBalance()- amountL);
											account.setAvailBalance(account.getAvailBalance()+ amountL);
											account.setLastTradingDay(newDateView);
											session.update("updateAccount",account);
											continue;
										} else {
											// 3. this transaction can be
											// executed
											// 1)update Transaction Table
											long shares = (long) (sharesD * 1000);
											trans.setShares(shares);
											trans.setExecuteDate(newDateView);
											trans.setStatus(1);
											trans.setPrice(newPrice);
											session.update("updateTransForTD",trans);
											// 2) update the Account Table
											account.setBalance(account.getBalance() - amountL);
											account.setFrozenBalance(account.getFrozenBalance()- amountL);
											account.setLastTradingDay(newDateView);
											session.update("updateAccount",account);
											// 3)update Position Table
											if (position != null) {
												long hasShares = position.getShares();
												long availSharesfromDB = position.getAvailShares();
												position.setShares(hasShares+ shares);
												position.setAvailShares(availSharesfromDB+ shares);
												session.update("updatePositionByTD",position);
											} else {
												position = new Position();
												position.setFundId(fundId);
												position.setUserId(userId);
												position.setShares(shares);
												position.setAvailShares(shares);
												session.insert("insertPositionByTD",position);
											}
										}
									} else if (transType == 1) {
										// sell
										Position searchCondition = new Position();
										searchCondition.setFundId(fundId);
										searchCondition.setUserId(userId);
										Position ptemp = session.selectOne("selectPositionByConditionForLock",searchCondition);
										Account account = session.selectOne("selectaccountbyaccountidForLock",accountId);
										long totalBalance = account.getBalance()+ newPrice* trans.getShares() / 1000;
										double totalBalanceforJudge = new Double(totalBalance / 100.0);
										// Condition1: if the desired
										// selling shares is
										// much more than the DB's
										// current shares
										if (sellShares > ptemp.getShares()) {
											trans.setStatus(2);
											trans.setErrorMessage("Your desired selling shares is much more "
													+ "than you own, this transaction has been cancelled!");
											session.update("updateTransForTD",trans);
											ptemp.setAvailShares(ptemp.getAvailShares()+ sellShares);
											ptemp.setFrozenShares(ptemp.getFrozenShares()- sellShares);
											session.update("updatePositionByTD", ptemp);
											continue;
										}
										// Judge is the total balance is
										// overflow
										// The Maximum of this balance
										// is 9999999999999.99

										else if (totalBalanceforJudge > 9999999999999.99) {
											trans.setStatus(2);
											trans.setErrorMessage("The maximum balance of your Account is $9,999,999,999,999.99, "
													+ "and after selling your desired funds, the account balance is over the maximum,"
													+ " so this transaction has been cancelled!");
											session.update("updateTransForTD",trans);
											ptemp.setAvailShares(ptemp.getAvailShares()+ sellShares);
											ptemp.setFrozenShares(ptemp.getFrozenShares()- sellShares);
											session.update("updatePositionByTD", ptemp);
											continue;
										}

										else {
											// Condition2 : this transaction
											// can be executed
											// 1.update Transaction
											// Table
											trans.setAmount(newPrice* trans.getShares() / 1000);
											trans.setExecuteDate(newDateView);
											trans.setStatus(1);
											trans.setPrice(newPrice);
											session.update("updateTransForTD",
													trans);
											// 2.update Account Table
											account.setBalance(account.getBalance()+ newPrice* trans.getShares() / 1000);
											account.setAvailBalance(account.getAvailBalance()+ newPrice* trans.getShares() / 1000);
											account.setLastTradingDay(newDateView);
											session.update("updateAccount",account);
											// 3.update Position Table
											if (ptemp != null) {
												long hasShares = ptemp.getShares();
												ptemp.setShares(hasShares- sellShares);
												ptemp.setAvailShares(ptemp.getAvailShares()- sellShares);
												if (hasShares - sellShares > 0) {
													session.update("updatePositionByTD",ptemp);
												} else {
													session.delete("deletePositionByTD",ptemp.getPositionId());
												}
											}
										}
									}
								}
							}
						}
						session.commit();
					}
					this.unLockFourTables(session, translist);
				} catch (NumberFormatException e) {
					this.unLockFourTables(session, translist);
					feedBack1 += "Transaction failure....";
				}
			} else {
				// Condition2: There is no pending transactions
				// Only update the Fund and FundPriceHistory tables
				try {
					session.update("lockFundTable");// Fund
					session.commit();

					if (fundVolist.size() > 0) {
						for (FundVO fundVo : fundVolist) {
							String fundName = fundVo.getFundName();
							double newPriceDouble = Double.parseDouble(fundVo.getNewPrice());
							newPriceDouble = FormatUtil.getdouble(newPriceDouble);
							long newPrice = (long) (newPriceDouble * 100);
							Fund fund = session.selectOne("selectfundbyfundname", fundName);
							int fundId = fund.getFundId();

							// step1: update the Fund Table
							fund.setFundValue(newPrice);
							fund.setLastTradingDate(newDateView);
							session.update("updateFund", fund);

							// step2: insert one record to the FundPriceHistory
							FundPriceHistory fph = new FundPriceHistory();
							fph.setFundId(fundId);
							fph.setPrice(newPrice);
							fph.setPriceDate(newDateView);
							session.insert("insertFundPriceHistory", fph);
							session.commit();
						}
					}

					session.update("unLockFundTable");
					session.commit();
				} catch (Exception e) {
					session.update("unLockFundTable");
					session.commit();
					feedBack1 += "Transaction failure...";
					return "failure";
				}
			}
		} catch (Exception e1) {
			this.unLockFourTables(session, translist);
			feedBack1 += "Transaction failure....";
			return "failure";
		} finally {
			session.close();
		}
		feedBack2 = "This Transaction has been processed successfully!";
		return "finish";
	}	
		
		/*try {
			Date maxDate;
			Date newDateView;
			//0. Judge whether need to lock all the pending transactions
			transactions = session.selectList("selectAllPendingTransactions");
			session.commit();
			if(transactions.size()<=0){
				feedBack1="There is no pending transactions!";
				return "failure";
			} else {
				// 1.judge is the date bigger than current biggest date;
				maxDate = session.selectOne("selectCurrentMaxTime");
				newDateView = DateUtil.stringToDate(CheckBadChar.fixBadChars(newDate));
				if (newDateView.compareTo(maxDate) <= 0) {
					feedBack1 ="This Day has been setted or your input date "
							+ "must be greater than the last tranding day of the database!";
					return "failure";
				} else {
					try {
						// real step to lock all the pending transactions
						session.update("lockTransactionTable");
						session.commit();
						if (fundVolist.size() > 0) {
							for (FundVO fundVo : fundVolist) {
								String fundName = fundVo.getFundName();
								double newPriceDouble = Double.parseDouble(fundVo.getNewPrice());
								newPriceDouble = FormatUtil.getdouble(newPriceDouble);
								long newPrice = (long) (newPriceDouble * 100);
								Fund fund = session.selectOne("selectfundbyfundname",fundName);
								int fundId = fund.getFundId();

								// step1: update the Fund Table
								fund.setFundValue(newPrice);
								fund.setLastTradingDate(newDateView);
								session.update("updateFund", fund);

								// step2: insert one record to the FundPriceHistory
								FundPriceHistory fph = new FundPriceHistory();
								fph.setFundId(fundId);
								fph.setPrice(newPrice);
								fph.setPriceDate(newDateView);
								session.insert("insertFundPriceHistory", fph);
								session.commit();

								// step3: retrieve all the records from transaction
								// table (Buy & Sell Pending)
								transactions = new ArrayList<Transaction>();
								transactions = session.selectList("selectTransactionsByFundIdForLock", fundId);
								
								if (transactions.size() > 0) {
									for (Transaction trans : transactions) {
										int transType = trans.getTransactionType();// 0-buy fund, 1-sell fund
										long amountL = trans.getAmount();
										double amount = amountL / 100.00d;
										int accountId = trans.getAccountId();
										int userId = trans.getUserId();
										//int positionIdFromTrans = trans.getPositionId();
										long sellShares = trans.getShares();
										// buy
										if (transType == 0) {
											double sharesD = amount / newPriceDouble;
											int positionId=0;
											//lock account table
											try {
												double currentShares=0.0d;
												Account account = session.selectOne("selectaccountbyaccountid",accountId);
												session.update("lockAccountTable",accountId);
												//lock position table
												Position searchCondition = new Position();
												searchCondition.setFundId(fundId);
												searchCondition.setUserId(userId);
												Position position = session.selectOne("selectPositionByCondition",searchCondition);
												if(position!=null){
													currentShares = new Double(position.getShares()/100.00);
													positionId = position.getPositionId();
													session.update("lockPositionTable",positionId);
												}
												session.commit();
												// 1.1 Underflow lower than 0.001
												if (sharesD < 0.001) {
													try {
														// update Transaction Table
														trans.setExecuteDate(newDateView);
														trans.setStatus(2);
														trans.setErrorMessage("The Price of the fund which you bought"
																+ " is much bigger than your Amount,this transaction has been cancelled!");
														session.update("updateTransForTD", trans);
														// 2)update the Account Table
														account.setFrozenBalance(account.getFrozenBalance() - amountL);
														account.setAvailBalance(account.getAvailBalance() + amountL);
														account.setLastTradingDay(newDateView);
														session.update("updateAccount", account);
														// 3)unlock the Account the table using
														// isLock="0"
														session.update("unLockAccountTable",accountId);
														session.update("unLockPositionTable",positionId);
														session.commit();
														continue;
													} catch (Exception e) {
														session.update("unLockAccountTable",accountId);
														session.update("unLockPositionTable",positionId);
														session.commit();
													}
												}
												
												//1.2 Over the Maximum of allowed Shares
												//The Maximum of shares is 100000000.000 in the database
												else if (sharesD >100000000.000||currentShares+sharesD>100000000.000) {
													try {
														// update Transaction Table
														trans.setExecuteDate(newDateView);
														trans.setStatus(2);
														trans.setErrorMessage("You cannot possess a fund's shares more than"+
														" 100,000,000.000, this transaction has been cancelled!");
														session.update("updateTransForTD", trans);
														// 2)update the Account Table
														account.setFrozenBalance(account.getFrozenBalance() - amountL);
														account.setAvailBalance(account.getAvailBalance() + amountL);
														account.setLastTradingDay(newDateView);
														session.update("updateAccount", account);
														// 3)unlock the Account the table using
														// isLock="0"
														session.update("unLockAccountTable",accountId);
														session.update("unLockPositionTable",positionId);
														session.commit();
														continue;
													} catch (Exception e) {
														session.update("unLockAccountTable",accountId);
														session.update("unLockPositionTable",positionId);
														session.commit();
													}
												}

												// 2.Overflow 1billion
												// Single Transaction amount's maximum is one billion
												else if (amount > AMOUNTUPBOUND) {
													try {
														// update Transaction Table
														trans.setStatus(2);
														trans.setExecuteDate(newDateView);
														trans.setErrorMessage("The Single Tranaction Amount is not allowed more than $1,000,000,000 "
																+ "and the transaction has been canceled!");
														session.update("updateTransForTD", trans);
														// 2)update the Account Table
														account.setFrozenBalance(account.getFrozenBalance() - amountL);
														account.setAvailBalance(account.getAvailBalance() + amountL);
														account.setLastTradingDay(newDateView);
														session.update("updateAccount", account);
														// 3)unlock the Account the table using
														// isLock="0"
														session.update("unLockAccountTable",accountId);
														session.update("unLockPositionTable",positionId);
														//session.update("unLockTransactionTable");
														session.commit();
													} catch (Exception e) {
														session.update("unLockAccountTable",accountId);
														session.update("unLockPositionTable",positionId);
														//session.update("unLockTransactionTable");
														session.commit();
														continue;
													}
													feedBack2="This Transaction has been processed successfully!";
													return "finish";
												}
												
												else{
													// 3. this transaction can be executed
													// 1)update Transaction Table
													long shares = (long) (sharesD * 1000);
													trans.setShares(shares);
													trans.setExecuteDate(newDateView);
													trans.setStatus(1);
													trans.setPrice(newPrice);
													session.update("updateTransForTD", trans);
													//2) update the Account Table
													account.setBalance(account.getBalance()- amountL);
													account.setFrozenBalance(account.getFrozenBalance() - amountL);
													account.setLastTradingDay(newDateView);
													session.update("updateAccount", account);
													 //3)update Position Table
													if(position!=null){
														long hasShares = position.getShares();
														long availSharesfromDB = position.getAvailShares();
														position.setShares(hasShares + shares);
														position.setAvailShares(availSharesfromDB+shares);
														session.update("updatePositionByTD",position);
													}else{
														position = new Position();
														position.setFundId(fundId);
														position.setUserId(userId);
														position.setShares(shares);
														position.setAvailShares(shares);
														session.insert("insertPositionByTD",position);
													}
													// 4)unlock the Account and Position Table
													session.update("unLockAccountTable",accountId);
													session.update("unLockPositionTable",positionId);
													session.commit();
												}
											} catch (Exception e) {
												e.printStackTrace();
												session.update("unLockAccountTable",accountId);
												session.update("unLockPositionTable",positionId);
												session.commit();
												
											}
										} else if (transType == 1) {

											int positionId = 0;
											// sell
											try {
												Position searchCondition = new Position();
												searchCondition.setFundId(fundId);
												searchCondition.setUserId(userId);
												Position ptemp = session.selectOne("selectPositionByCondition",searchCondition);
												if (ptemp != null) {
													positionId = ptemp.getPositionId();
													session.update("lockPositionTable",positionId);
													session.commit();
												}
												Account account = session.selectOne("selectaccountbyaccountid",accountId);
												long totalBalance = account.getBalance()+ newPrice* trans.getShares()/ 1000;
												double totalBalanceforJudge = new Double(totalBalance / 100.0);
												// Condition1: if the desired
												// selling shares is
												// much more than the DB's
												// current shares
												if (sellShares > ptemp.getShares()) {
													try {
														trans.setStatus(2);
														trans.setErrorMessage("Your desired selling shares is much more "
																+ "than you own, this transaction has been cancelled!");
														session.update("updateTransForTD",trans);
														ptemp.setAvailShares(ptemp.getAvailShares()+ sellShares);
														ptemp.setFrozenShares(ptemp.getFrozenShares()- sellShares);
														session.update("updatePositionByTD",ptemp);
														session.update("unLockPositionTable",positionId);
														session.commit();
														continue;
													} catch (Exception e) {
														e.printStackTrace();
														session.update("unLockPositionTable",positionId);
														session.commit();
													}
												}
												// Judge is the total balance is
												// overflow
												// The Maximum of this balance
												// is 9999999999999.99
												
												else if (totalBalanceforJudge > 9999999999999.99) {
													try {
														trans.setStatus(2);
														trans.setErrorMessage("The maximum balance of your Account is $9,999,999,999,999.99, "
																+ "and after selling your desired funds, the account balance is over the maximum,"
																+ " so this transaction has been cancelled!");
														session.update("updateTransForTD",trans);
														ptemp.setAvailShares(ptemp.getAvailShares()+ sellShares);
														ptemp.setFrozenShares(ptemp.getFrozenShares()- sellShares);
														session.update("updatePositionByTD",ptemp);
														session.update("unLockPositionTable",positionId);
														session.commit();
														continue;
													} catch (Exception e) {
														e.printStackTrace();
														session.update("unLockPositionTable",positionId);
														session.commit();
													}
												}
												
												else{
													// Condition2 : this transaction
													// can be executed
													try {
														// 0.lock the Account and
														// position table using
														// isLock="1"
														session.update("lockAccountTable",accountId);
														session.commit();
														// 1.update Transaction
														// Table
														trans.setAmount(newPrice* trans.getShares()/ 1000);
														trans.setExecuteDate(newDateView);
														trans.setStatus(1);
														trans.setPrice(newPrice);
														session.update("updateTransForTD",trans);
														// 2.update Account Table
														account.setBalance(account.getBalance()+ newPrice* trans.getShares()/ 1000);
														account.setAvailBalance(account.getAvailBalance()+ newPrice* trans.getShares()/ 1000);
														account.setLastTradingDay(newDateView);
														session.update("updateAccount",account);
														// 3.update Position Table
														if (ptemp != null) {
															long hasShares = ptemp.getShares();
															ptemp.setShares(hasShares- sellShares);
															ptemp.setAvailShares(ptemp.getAvailShares()- sellShares);
															if (hasShares- sellShares > 0) {
																session.update("updatePositionByTD",ptemp);
															} else {
																session.delete("deletePositionByTD",ptemp.getPositionId());
															}
														}
														// 4.unlock the two tables
														session.update("unLockAccountTable",accountId);
														session.update("unLockPositionTable",positionId);
														session.commit();
													} catch (Exception e) {
														e.printStackTrace();
														session.update("unLockAccountTable",accountId);
														session.update("unLockPositionTable",positionId);
														session.commit();
													}
												}
											} catch (Exception e) {
												e.printStackTrace();
												session.update("unLockAccountTable",accountId);
												session.update("unLockPositionTable",positionId);
												session.commit();
											}
										}
									}
								}
							}
						}
						//final unlock the transactions table
						session.update("unLockTransactionTable");
						session.commit();
					} catch (Exception e) {
						session.update("unLockTransactionTable");
						session.commit();
					}
				}
			}
		} finally {
			session.close();
		}
		feedBack2="This Transaction has been processed successfully!";
		return "finish";
	}*/
	
	public void LockFourTables(SqlSession session,List<Transaction> translist) throws IOException{
		for(Transaction tran:translist){
			if(tran!=null){
				if(tran.getAccountId()>0){
					session.update("lockAccountTable", tran.getAccountId());//1)update account
				}
				
				if(tran.getPositionId()>0){
					session.update("lockPositionTable", tran.getPositionId());//2)update position
				}
			}
		}
		session.update("lockTransactionTable");//3)Transaction
		session.update("lockFundTable");//4)Fund
		session.commit();
	}
	
	public void unLockFourTables(SqlSession session,List<Transaction> translist) throws IOException{
		for(Transaction tran:translist){
			if(tran!=null){
				if(tran.getAccountId()>0){
					session.update("unLockAccountTable", tran.getAccountId());//1)update account
				}
				if(tran.getPositionId()>0){
					session.update("unLockPositionTable", tran.getPositionId());//2)update position
				}
			}
		}
		session.update("unLockTransactionTable");//3)Transaction
		session.update("unLockFundTable");//4)Fund
		session.commit();
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public List<Fund> getFundlist() {
		return fundlist;
	}

	public void setFundlist(List<Fund> fundlist) {
		this.fundlist = fundlist;
	}

	public List<FundVO> getFundVolist() {
		return fundVolist;
	}

	public void setFundVolist(List<FundVO> fundVolist) {
		this.fundVolist = fundVolist;
	}

	public String getNewDate() {
		return newDate;
	}

	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getFeedBack1() {
		return feedBack1;
	}

	public void setFeedBack1(String feedBack1) {
		this.feedBack1 = feedBack1;
	}

	public String getFeedBack2() {
		return feedBack2;
	}

	public void setFeedBack2(String feedBack2) {
		this.feedBack2 = feedBack2;
	}

}
