package com.mak.demo.poll.manager.rdms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    Stream<T> findByIdIn(Set<Long> ids);

   /* @Query("update #{#entityName} e set e.deletedAt= CURRENT_TIMESTAMP where e.id IN ?1")
    @Modifying
    void deleteByIdIn(Set<Long> idSet);

    @Override
    @Query("update #{#entityName} e set e.deletedAt= CURRENT_TIMESTAMP where e.id = ?1")
    @Modifying
    void deleteById(Long id);

    @Override
    @Query("update #{#entityName} e set e.deletedAt= CURRENT_TIMESTAMP where e = ?1")
    @Modifying
    void delete(T t);

    @Query("update #{#entityName} e set e.deletedAt= CURRENT_TIMESTAMP where e.id IN ?1")
    @Modifying
    void deleteByIds(Collection<Long> names);*/

    T findTopByOrderByUpdatedAtDesc();

}

