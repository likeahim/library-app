package com.rent.library.repository;

import com.rent.library.domain.Copy;
import com.rent.library.domain.Status;
import com.rent.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {

    List<Copy> findCopiesByTitleIdAndStatus(Long titleId, Status status);

}
