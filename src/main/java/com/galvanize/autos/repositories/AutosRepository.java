package com.galvanize.autos.repositories;

import com.galvanize.autos.model.Automobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutosRepository extends JpaRepository<Automobile, Long> {
}
