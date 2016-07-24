package com.etf.volume.simulator.service.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.etf.volume.simulator.service.entity.Ticker;

@Repository
@Transactional
public class VolumeSimulatorDAOImpl implements VolumeSimulatorDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession() {
		try {
			return this.sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			return this.sessionFactory.openSession();
		}
	}

	@Override
	public void saveExistantTickerDetails(Ticker ticker) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.persist(ticker);
		tx.commit();
		session.close();
	}

	@Override
	public void saveTickerDetails(Ticker ticker) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.persist(ticker);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticker> getTickerList() {
		Session session = getSession();
		List<Ticker> tickerList = session.createQuery("from ticker").list();
		session.close();
		return tickerList;
	}

}
