package com.treinamentoJasper.spring_jasper_treinamento.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ReadOnlyRepository<T,ID> extends Repository<T, ID> {
    Optional<T> findById(ID id);
    
    boolean existsById(ID id);
    
    List<T> findAll();
    
    List<T> findAllById(Iterable<ID> ids);
    
    long count();
    
    T getOne(ID id);
}
