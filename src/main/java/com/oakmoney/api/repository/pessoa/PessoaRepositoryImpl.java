package com.oakmoney.api.repository.pessoa;

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

import com.oakmoney.api.model.Endereco_;
import com.oakmoney.api.model.Pessoa;
import com.oakmoney.api.model.Pessoa_;
import com.oakmoney.api.repository.PessoaRepositoryQuery;
import com.oakmoney.api.repository.filter.PessoaFilter;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<Pessoa> findByFilter(PessoaFilter filter, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		
		
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(builder.asc(root.get(Pessoa_.nome)));
		criteriaQuery.orderBy(orderList);
		
		TypedQuery<Pessoa> query = entityManager.createQuery(criteriaQuery);
		
		restricaoPaginacao(pageable, query);
		
		return new PageImpl<>(query.getResultList(), pageable, countByFilter(filter));
	}

	private Long countByFilter(PessoaFilter filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.select(builder.count(root));
		
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	private void restricaoPaginacao(Pageable pageable, TypedQuery<Pessoa> query) {
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
	}

	private Predicate[] criarRestricoes(PessoaFilter filter, CriteriaBuilder builder, Root<Pessoa> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (null != filter.getAtivo()) {
			predicates.add(builder.equal(root.get(Pessoa_.ativo), filter.getAtivo()));
		}
		if (StringUtils.isNotBlank(filter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.nome)), 
						"%"+filter.getNome().toLowerCase()+"%"));
		}
		if (StringUtils.isNotBlank(filter.getBairro())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.endereco).get(Endereco_.bairro)), 
						"%"+filter.getBairro().toLowerCase()+"%"));
		}
		if (StringUtils.isNotBlank(filter.getCidade())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.endereco).get(Endereco_.cidade)), 
						"%"+filter.getCidade().toLowerCase()+"%"));
		}
		if (StringUtils.isNotBlank(filter.getEstado())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.endereco).get(Endereco_.estado)), 
						"%"+filter.getEstado().toLowerCase()+"%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
