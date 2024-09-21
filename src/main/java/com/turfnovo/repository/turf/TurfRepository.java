package com.turfnovo.repository.turf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turfnovo.model.turf.Turf;
import com.turfnovo.model.user.User;

public interface TurfRepository extends JpaRepository<Turf, Long> {
    List<Turf> findByOwner(User owner);
}
