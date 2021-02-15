package dev.ershov.vd.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdapterRepository extends JpaRepository<Adapter, String> {
}
