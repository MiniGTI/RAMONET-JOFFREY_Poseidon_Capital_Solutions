package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to perform CRUDs request to the BidList table.
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
