package com.oakmoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.oakmoney.api.model.Lancamento;
import com.oakmoney.api.model.Lancamento_;
import com.oakmoney.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(builder.asc(root.get(Lancamento_.dataVencimento)));
		orderList.add(builder.asc(root.get(Lancamento_.tipo)));
		orderList.add(builder.asc(root.get(Lancamento_.categoria.getName())));
		criteriaQuery.orderBy(orderList);

		TypedQuery<Lancamento> query = entityManager.createQuery(criteriaQuery);
		
		
		restricaoPaginacao(pageable, query);
		
		
		return new PageImpl<>(query.getResultList(), pageable, countByFilter(filter));
	}

	private Long countByFilter(LancamentoFilter filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.select(builder.count(root));
		
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(filter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + filter.getDescricao().toLowerCase() + "%"));
		}

		if (filter.getDataVencimentoInicial() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento),
					filter.getDataVencimentoInicial()));
		}

		if (filter.getDataVencimentoFinal() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoFinal()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void restricaoPaginacao(Pageable pageable, TypedQuery<Lancamento> query) {
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
	}

}
