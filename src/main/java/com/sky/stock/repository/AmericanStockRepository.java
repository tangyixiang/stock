package com.sky.stock.repository;

import com.sky.stock.entity.AmericanStockInfo;
import org.springframework.data.repository.CrudRepository;

public interface AmericanStockRepository extends CrudRepository<AmericanStockInfo, String> {
}
