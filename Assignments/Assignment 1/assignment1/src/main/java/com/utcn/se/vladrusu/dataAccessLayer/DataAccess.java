package com.utcn.se.vladrusu.dataAccessLayer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.utcn.se.vladrusu.model.ActivityLog;
import com.utcn.se.vladrusu.model.Account;
import com.utcn.se.vladrusu.model.ClientAccount;
import com.utcn.se.vladrusu.model.ClientBankAccount;
import com.utcn.se.vladrusu.model.EmployeeAccount;
import com.utcn.se.vladrusu.model.ModelEntity;
import com.utcn.se.vladrusu.model.UtilityBill;

@Repository("dao")
@Qualifier("dao")
public class DataAccess implements IDataAccess {

	private SessionFactory sessionFactory;
	private Session session;

	@Autowired
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ModelEntity save(ModelEntity modelEntity) {
		Integer id = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			id = (Integer) session.save(modelEntity);

			if (id != null)
				modelEntity.setId(id);

			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			this.update(modelEntity);
		}

		return modelEntity;

	}

	public boolean update(ModelEntity modelEntity) {
		try {

			session = sessionFactory.openSession();
			session.beginTransaction();

			session.update(modelEntity);

			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public List<ClientAccount> getAllClients() {
		List<ClientAccount> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(ClientAccount.class).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<EmployeeAccount> getAllEmployees() {
		List<EmployeeAccount> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(EmployeeAccount.class).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public ClientAccount getClientById(int id) {
		List<ClientAccount> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(ClientAccount.class).add(Restrictions.eq("clientId", id)).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ClientAccount getClientByCNP(String cnp) {
		List<ClientAccount> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(ClientAccount.class).add(Restrictions.eq("cnp", cnp)).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Account> getBankAccountsByClientId(int id) {
		List<ClientBankAccount> cba = null;
		List<Account> bankAccounts = new ArrayList<Account>();

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			ClientAccount ca = (ClientAccount) session.createCriteria(ClientAccount.class)
					.add(Restrictions.eq("clientId", id)).list().get(0);

			cba = session.createCriteria(ClientBankAccount.class).add(Restrictions.eq("clientId", ca.getId())).list();

			for (ClientBankAccount it : cba) {
				bankAccounts.add((Account) session.createCriteria(Account.class)
						.add(Restrictions.eq("accountId", it.getAccountId())).list().get(0));
			}

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return bankAccounts;
	}

	@SuppressWarnings("unchecked")
	public List<Account> getAllBankAccounts() {
		List<Account> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(Account.class).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public EmployeeAccount getEmployeeById(int id) {
		List<EmployeeAccount> list = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			list = (List<EmployeeAccount>)session.createCriteria(EmployeeAccount.class).add(Restrictions
					.eq("employeeId", id)).list();
			
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			 session.close();
		}
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public EmployeeAccount getEmployeeByUsername(String username) {
		List<EmployeeAccount> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(EmployeeAccount.class).add(Restrictions.eq("username", username)).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		if (!list.isEmpty())
			return list.get(0);
		else
			return null;
	}

	public boolean delete(ModelEntity modelEntity) {
		try {

			session = sessionFactory.openSession();
			session.beginTransaction();

			session.delete(modelEntity);

			session.getTransaction().commit();
			session.clear();
			session.close();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
			return false;
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public ClientBankAccount getClientBankAccount(int clientId, int accountId) {
		List<ClientBankAccount> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(ClientBankAccount.class).add(Restrictions.eq("clientId", clientId))
					.add(Restrictions.eq("accountId", accountId)).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		if (list != null)
			return list.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<ActivityLog> getActivityLogByEmployeeId(int id) {
		List<ActivityLog> list = null;
		if (getEmployeeById(id) != null) {
			int employeeId = id;

			try {
				session = sessionFactory.openSession();

				session.beginTransaction();

				list = session.createCriteria(ActivityLog.class).add(Restrictions.eq("employeeId", employeeId)).list();

				session.getTransaction().commit();

			} catch (Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}

		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public UtilityBill getUtilityBill(int billId) {
		List<UtilityBill> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(UtilityBill.class).add(Restrictions.eq("billId", billId)).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		if (list != null) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UtilityBill> getAllBills() {
		List<UtilityBill> list = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			list = session.createCriteria(UtilityBill.class).list();

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return list;
	}

}
